from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Positions, Licences
import json

class PositionsView:

    @staticmethod
    @csrf_exempt
    def get_all_positions(request):
        """
        Get all positions.
        Example response JSON:
        {
            "positions": [
                {
                    "id": 1,
                    "name": "Head Coach",
                    "salary_min": "5000.00",
                    "salary_max": "8000.00",
                    "licence": {
                        "id": 1,
                        "licence_level": "Advanced"
                    },
                    "coaching_licence": {
                        "id": 2,
                        "licence_level": "Basic"
                    },
                    "speciality": "Jumping"
                },
                {
                    "id": 2,
                    "name": "Trainer",
                    "salary_min": "3000.00",
                    "salary_max": "6000.00",
                    "licence": {
                        "id": 2,
                        "licence_level": "Basic"
                    },
                    "coaching_licence": {
                        "id": null,
                        "licence_level": null
                    },
                    "speciality": "Dressage"
                }
            ]
        }
        """
        try:
            positions = Positions.objects.select_related('licence', 'coaching_licence').values(
                'id', 'name', 'salary_min', 'salary_max',
                'licence__id', 'licence__licence_level',
                'coaching_licence__id', 'coaching_licence__licence_level',
                'speciality'
            )

            data = [
                {
                    "id": position['id'],
                    "name": position['name'],
                    "salary_min": str(position['salary_min']),
                    "salary_max": str(position['salary_max']),
                    "licence": {
                        "id": position['licence__id'],
                        "licence_level": position['licence__licence_level']
                    } if position['licence__id'] else None,
                    "coaching_licence": {
                        "id": position['coaching_licence__id'],
                        "licence_level": position['coaching_licence__licence_level']
                    } if position['coaching_licence__id'] else None,
                    "speciality": position['speciality']
                }
                for position in positions
            ]
            return JsonResponse({'positions': data}, status=200)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_position_by_id(request):
        """
        Get positions by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "positions": [
                {
                    "id": 1,
                    "name": "Head Coach",
                    "salary_min": "5000.00",
                    "salary_max": "8000.00",
                    "licence": {
                        "id": 1,
                        "licence_level": "Advanced"
                    },
                    "coaching_licence": {
                        "id": 2,
                        "licence_level": "Basic"
                    },
                    "speciality": "Jumping"
                },
                {
                    "id": 2,
                    "name": "Trainer",
                    "salary_min": "3000.00",
                    "salary_max": "6000.00",
                    "licence": {
                        "id": 2,
                        "licence_level": "Basic"
                    },
                    "coaching_licence": {
                        "id": null,
                        "licence_level": null
                    },
                    "speciality": "Dressage"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Position ID is required'}, status=400)

            positions = Positions.objects.filter(id__in=ids).select_related('licence', 'coaching_licence').values(
                'id', 'name', 'salary_min', 'salary_max',
                'licence__id', 'licence__licence_level',
                'coaching_licence__id', 'coaching_licence__licence_level',
                'speciality'
            )

            data = [
                {
                    "id": position['id'],
                    "name": position['name'],
                    "salary_min": str(position['salary_min']),
                    "salary_max": str(position['salary_max']),
                    "licence": {
                        "id": position['licence__id'],
                        "licence_level": position['licence__licence_level']
                    } if position['licence__id'] else None,
                    "coaching_licence": {
                        "id": position['coaching_licence__id'],
                        "licence_level": position['coaching_licence__licence_level']
                    } if position['coaching_licence__id'] else None,
                    "speciality": position['speciality']
                }
                for position in positions
            ]
            return JsonResponse({'positions': data}, status=200)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_position(request):
        """
        Add new positions.
        Example JSON payload:
        {
            "positions": [
                {
                    "name": "Head Coach",
                    "salary_min": "5000.00",
                    "salary_max": "8000.00",
                    "licence": {
                        "id": 1
                    },
                    "coaching_licence": {
                        "id": 2
                    },
                    "speciality": "Jumping"
                },
                {
                    "name": "Trainer",
                    "salary_min": "3000.00",
                    "salary_max": "6000.00",
                    "licence": {
                        "id": 2
                    },
                    "coaching_licence": null,
                    "speciality": "Dressage"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Positions added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            positions = data.get('positions', [])
            position_ids = []
            with transaction.atomic():
                for position in positions:
                    name = position.get('name')
                    salary_min = position.get('salary_min')
                    salary_max = position.get('salary_max')
                    licence_id = position.get('licence', {}).get('id')
                    coaching_licence_id = position.get('coaching_licence', {}).get('id') if position.get('coaching_licence') else None
                    speciality = position.get('speciality')

                    if not name or not salary_min or not salary_max or not licence_id or not speciality:
                        return JsonResponse({'error': 'All fields (name, salary_min, salary_max, licence_id, speciality) are required'}, status=400)

                    new_position = Positions.objects.create(
                        name=name,
                        salary_min=salary_min,
                        salary_max=salary_max,
                        licence_id=licence_id,
                        coaching_licence_id=coaching_licence_id,
                        speciality=speciality
                    )
                    position_ids.append(new_position.id)
            return JsonResponse({'message': 'Positions added successfully', 'ids': position_ids}, status=201)
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
    def update_position(request):
        """
        Update positions by IDs.
        Example JSON payload:
        {
            "positions": [
                {
                    "id": 1,
                    "name": "Updated Head Coach",
                    "salary_min": "6000.00",
                    "salary_max": "9000.00",
                    "licence": {
                        "id": 2
                    },
                    "coaching_licence": {
                        "id": null
                    },
                    "speciality": "Updated Jumping"
                },
                {
                    "id": 2,
                    "name": "Updated Trainer",
                    "salary_min": "3500.00",
                    "salary_max": "6500.00",
                    "licence": {
                        "id": 1
                    },
                    "coaching_licence": {
                        "id": 2
                    },
                    "speciality": "Updated Dressage"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Positions updated successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            positions = data.get('positions', [])
            updated_ids = []
            with transaction.atomic():
                for position in positions:
                    position_id = position.get('id')
                    name = position.get('name')
                    salary_min = position.get('salary_min')
                    salary_max = position.get('salary_max')
                    licence_id = position.get('licence', {}).get('id')
                    coaching_licence_id = position.get('coaching_licence', {}).get('id') if position.get('coaching_licence') else None
                    speciality = position.get('speciality')

                    if not position_id or not name or not salary_min or not salary_max or not licence_id or not speciality:
                        return JsonResponse({'error': 'ID, name, salary_min, salary_max, licence_id, speciality are required fields'}, status=400)

                    Positions.objects.filter(id=position_id).update(
                        name=name,
                        salary_min=salary_min,
                        salary_max=salary_max,
                        licence_id=licence_id,
                        coaching_licence_id=coaching_licence_id,
                        speciality=speciality
                    )
                    updated_ids.append(position_id)
            return JsonResponse({'message': 'Positions updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_positions(request):
        """
        Delete positions by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "message": "Positions deleted successfully",
            "deleted_ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Position ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for position_id in ids:
                    Positions.objects.filter(id=position_id).delete()
                    deleted_ids.append(position_id)
            return JsonResponse({'message': 'Positions deleted successfully', 'deleted_ids': deleted_ids}, status=200)
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
