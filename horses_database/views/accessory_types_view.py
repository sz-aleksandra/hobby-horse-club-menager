from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import AccessoryTypes
import json


class AccessoryTypesView:
    @staticmethod
    @csrf_exempt
    def get_all_accessory_types(request):
        """
        Get all accessory types.
        Example response JSON:
        {
            "accessory_types": [
                {
                    "id": 1,
                    "type_name": "Saddle"
                },
                {
                    "id": 2,
                    "type_name": "Bridle"
                }
            ]
        }
        """
        try:
            accessory_types = list(AccessoryTypes.objects.all().values())
            return JsonResponse({'accessory_types': accessory_types}, status=200)

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
    def get_accessory_types_by_id(request):
        """
        Get accessory types by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "accessory_types": [
                {
                    "id": 1,
                    "type_name": "Saddle"
                },
                {
                    "id": 2,
                    "type_name": "Bridle"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Accessory Type ID is required'}, status=400)

            accessory_types = list(AccessoryTypes.objects.filter(id__in=ids).values())
            return JsonResponse({'accessory_types': accessory_types}, status=200)

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
    def add_accessory_type(request):
        """
        Add new accessory types.
        Example JSON payload:
        {
            "accessory_types": [
                {
                    "type_name": "Saddle"
                },
                {
                    "type_name": "Bridle"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Accessory types added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            accessory_types = data.get('accessory_types', [])

            if not accessory_types:
                return JsonResponse({'error': 'No accessory types provided'}, status=400)

            accessory_type_ids = []
            with transaction.atomic():
                for accessory_type in accessory_types:
                    type_name = accessory_type.get('type_name')
                    if not type_name:
                        return JsonResponse({'error': 'Type name is required'}, status=400)

                    new_accessory_type = AccessoryTypes.objects.create(
                        type_name=type_name
                    )
                    accessory_type_ids.append(new_accessory_type.id)
            return JsonResponse({'message': 'Accessory types added successfully', 'ids': accessory_type_ids}, status=201)
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
    def update_accessory_type(request):
        """
        Update accessory types by IDs.
        Example JSON payload:
        {
            "accessory_types": [
                {
                    "id": 4,
                    "type_name": "Updated Saddle"
                },
                {
                    "id": 5,
                    "type_name": "Updated Bridle"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Accessory types updated successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            accessory_types = data.get('accessory_types', [])

            if not accessory_types:
                return JsonResponse({'error': 'No accessory types provided'}, status=400)

            updated_ids = []
            with transaction.atomic():
                for accessory_type in accessory_types:
                    accessory_type_id = accessory_type.get('id')
                    type_name = accessory_type.get('type_name')
                    if not accessory_type_id or not type_name:
                        return JsonResponse({'error': 'ID and type_name are required fields'}, status=400)

                    AccessoryTypes.objects.filter(id=accessory_type_id).update(
                        type_name=type_name
                    )
                    updated_ids.append(accessory_type_id)
            return JsonResponse({'message': 'Accessory types updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_accessory_type(request):
        """
        Delete accessory types by IDs.
        Example JSON payload:
        {
            "ids": [4, 5]
        }
        Example response JSON:
        {
            "message": "Accessory types deleted successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Accessory Type ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for accessory_type_id in ids:
                    AccessoryTypes.objects.filter(id=accessory_type_id).delete()
                    deleted_ids.append(accessory_type_id)
            return JsonResponse({'message': 'Accessory types deleted successfully', 'ids': deleted_ids}, status=200)
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
