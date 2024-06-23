from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Groups
import json


class GroupsView:
    @staticmethod
    @csrf_exempt
    def get_all_groups(request):
        """
        Get all groups.
        Example request JSON: N/A
        Example response JSON:
        {
            "groups": [
                {
                    "id": 1,
                    "name": "Group A",
                    "max_group_members": 10
                },
                {
                    "id": 2,
                    "name": "Group B",
                    "max_group_members": 15
                }
            ]
        }
        """
        try:
            groups = list(Groups.objects.all().values())
            return JsonResponse({'groups': groups}, status=200)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_group_by_id(request):
        """
        Get groups by IDs.
        Example JSON request:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "groups": [
                {
                    "id": 1,
                    "name": "Group A",
                    "max_group_members": 10
                },
                {
                    "id": 2,
                    "name": "Group B",
                    "max_group_members": 15
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Group ID is required'}, status=400)

            groups = list(Groups.objects.filter(id__in=ids).values())
            return JsonResponse({'groups': groups}, status=200)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_new_group(request):
        """
        Add new groups.
        Example JSON request:
        {
            "groups": [
                {
                    "name": "Group A",
                    "max_group_members": 10
                },
                {
                    "name": "Group B",
                    "max_group_members": 15
                }
            ]
        }
        Example response JSON:
        {
            "message": "Groups added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            groups = data.get('groups', [])

            if not groups:
                return JsonResponse({'error': 'No groups provided'}, status=400)

            group_ids = []
            with transaction.atomic():
                for group in groups:
                    name = group.get('name')
                    max_group_members = group.get('max_group_members')
                    if not name or not max_group_members:
                        return JsonResponse({'error': 'Name and max_group_members are required fields'}, status=400)

                    new_group = Groups.objects.create(
                        name=name,
                        max_group_members=max_group_members
                    )
                    group_ids.append(new_group.id)
            return JsonResponse({'message': 'Groups added successfully', 'ids': group_ids}, status=201)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def update_group(request):
        """
        Update groups by IDs.
        Example JSON request:
        {
            "groups": [
                {
                    "id": 4,
                    "name": "Updated Group A",
                    "max_group_members": 12
                },
                {
                    "id": 5,
                    "name": "Updated Group B",
                    "max_group_members": 18
                }
            ]
        }
        Example response JSON:
        {
            "message": "Groups updated successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            groups = data.get('groups', [])

            if not groups:
                return JsonResponse({'error': 'No groups provided'}, status=400)

            updated_ids = []
            with transaction.atomic():
                for group in groups:
                    group_id = group.get('id')
                    name = group.get('name')
                    max_group_members = group.get('max_group_members')
                    if not group_id or not name or not max_group_members:
                        return JsonResponse({'error': 'ID, name, and max_group_members are required fields'}, status=400)

                    Groups.objects.filter(id=group_id).update(
                        name=name,
                        max_group_members=max_group_members
                    )
                    updated_ids.append(group_id)
            return JsonResponse({'message': 'Groups updated successfully', 'ids': updated_ids}, status=200)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def delete_group(request):
        """
        Delete groups by IDs.
        Example JSON request:
        {
            "ids": [4, 5]
        }
        Example response JSON:
        {
            "message": "Groups deleted successfully",
            "deleted_ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Group ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for group_id in ids:
                    Groups.objects.filter(id=group_id).delete()
                    deleted_ids.append(group_id)
            return JsonResponse({'message': 'Groups deleted successfully', 'deleted_ids': deleted_ids}, status=200)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)
