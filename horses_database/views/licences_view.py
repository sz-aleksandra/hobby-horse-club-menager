from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from horses_database.models import Licences
import json
from django.db import transaction, IntegrityError, DatabaseError


class LicencesView:
    @staticmethod
    @csrf_exempt
    def get_all_licences(request):
        """
        Get all licences.
        Example response JSON:
        {
            "licences": [
                {
                    "id": 1,
                    "licence_level": "Basic"
                },
                {
                    "id": 2,
                    "licence_level": "Advanced"
                }
            ]
        }
        """
        try:
            licences = list(Licences.objects.all().values())
            return JsonResponse({'licences': licences}, status=200)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_licence_by_id(request):
        """
        Get licences by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "licences": [
                {
                    "id": 1,
                    "licence_level": "Basic"
                },
                {
                    "id": 2,
                    "licence_level": "Advanced"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Licence ID is required'}, status=400)

            licences = list(Licences.objects.filter(id__in=ids).values())
            return JsonResponse({'licences': licences}, status=200)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_new_licence(request):
        """
        Add new licences.
        Example JSON payload:
        {
            "licences": [
                {
                    "licence_level": "Basic"
                },
                {
                    "licence_level": "Advanced"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Licences added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            licences = data.get('licences', [])
            licence_ids = []
            with transaction.atomic():
                for licence in licences:
                    licence_level = licence.get('licence_level')
                    if not licence_level:
                        return JsonResponse({'error': 'Licence level is required'}, status=400)

                    new_licence = Licences.objects.create(
                        licence_level=licence_level
                    )
                    licence_ids.append(new_licence.id)
            return JsonResponse({'message': 'Licences added successfully', 'ids': licence_ids}, status=201)
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
    def update_licence(request):
        """
        Update licences by IDs.
        Example JSON payload:
        {
            "licences": [
                {
                    "id": 4,
                    "licence_level": "Updated Basic"
                },
                {
                    "id": 5,
                    "licence_level": "Updated Advanced"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Licences updated successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            licences = data.get('licences', [])
            updated_ids = []
            with transaction.atomic():
                for licence in licences:
                    licence_id = licence.get('id')
                    licence_level = licence.get('licence_level')
                    if not licence_id or not licence_level:
                        return JsonResponse({'error': 'ID and licence_level are required fields'}, status=400)

                    Licences.objects.filter(id=licence_id).update(
                        licence_level=licence_level
                    )
                    updated_ids.append(licence_id)
            return JsonResponse({'message': 'Licences updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_licence(request):
        """
        Delete licences by IDs.
        Example JSON payload:
        {
            "ids": [4, 5]
        }
        Example response JSON:
        {
            "message": "Licences deleted successfully",
            "deleted_ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Licence ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for licence_id in ids:
                    Licence.objects.filter(id=licence_id).delete()
                    deleted_ids.append(licence_id)
            return JsonResponse({'message': 'Licences deleted successfully', 'deleted_ids': deleted_ids}, status=200)
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
