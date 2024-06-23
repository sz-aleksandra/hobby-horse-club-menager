from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Positions, Licences, Employees, PositionsHistory
import json

class PositionsHistoryView:

    @staticmethod
    @csrf_exempt
    def get_all_positions_history(request):
        """
        Get all positions history.
        Example request JSON: N/A
        Example response JSON:
        {
            "positions_history": [
                {
                    "employee": {
                        "id": 1,
                        "member": {
                            "id": 1,
                            "name": "John",
                            "surname": "Doe",
                            "username": "johndoe",
                            "date_of_birth": "1990-01-01",
                            "address": {
                                "id": 1,
                                "country": "USA",
                                "city": "New York",
                                "street": "Broadway",
                                "street_no": "123",
                                "postal_code": "10001"
                            },
                            "phone_number": "+1234567890",
                            "email": "john.doe@example.com",
                            "is_active": true,
                            "licence": {
                                "id": 1,
                                "licence_level": "A"
                            }
                        }
                    },
                    "position" {
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
                    "date_start": "1990-01-01",
                    "date_end": "1990-01-01"
                }
            ]
        }
        """
        try:
            positions_history = PositionsHistory.objects.select_related(
                'employee__member__address', 'employee__member__licence', 'position__licence',
                'position__coaching_licence'
            ).all()

            response_data = []
            for ph in positions_history:
                member = ph.employee.member
                address = member.address
                member_licence = member.licence
                position = ph.position
                position_licence = position.licence
                coaching_licence = position.coaching_licence

                member_data = {
                    "id": member.id,
                    "name": member.name,
                    "surname": member.surname,
                    "username": member.username,
                    "date_of_birth": str(member.date_of_birth),
                    "address": {
                        "id": address.id,
                        "country": address.country,
                        "city": address.city,
                        "street": address.street,
                        "street_no": address.street_no,
                        "postal_code": address.postal_code
                    },
                    "phone_number": member.phone_number,
                    "email": member.email,
                    "is_active": member.is_active,
                    "licence": {
                        "id": member_licence.id,
                        "licence_level": member_licence.licence_level
                    }
                }

                position_data = {
                    "id": position.id,
                    "name": position.name,
                    "salary_min": str(position.salary_min),
                    "salary_max": str(position.salary_max),
                    "licence": {
                        "id": position_licence.id,
                        "licence_level": position_licence.licence_level
                    },
                    "coaching_licence": {
                        "id": coaching_licence.id,
                        "licence_level": coaching_licence.licence_level
                    },
                    "speciality": position.speciality
                }

                response_data.append({
                    "employee": {
                        "id": ph.employee.id,
                        "member": member_data
                    },
                    "position": position_data,
                    "date_start": str(ph.date_start),
                    "date_end": str(ph.date_end) if ph.date_end else None
                })

            return JsonResponse({"positions_history": response_data}, status=200)
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
    def get_position_history_by_id(request):
        """
        Get all positions for IDs
        Example request JSON:
        {
            "ids": [1]
        }
        Example response JSON:
        {
            "positions_history": [
                {
                    "employee": {
                        "id": 1,
                        "member": {
                            "id": 1,
                            "name": "John",
                            "surname": "Doe",
                            "username": "johndoe",
                            "date_of_birth": "1990-01-01",
                            "address": {
                                "id": 1,
                                "country": "USA",
                                "city": "New York",
                                "street": "Broadway",
                                "street_no": "123",
                                "postal_code": "10001"
                            },
                            "phone_number": "+1234567890",
                            "email": "john.doe@example.com",
                            "is_active": true,
                            "licence": {
                                "id": 1,
                                "licence_level": "A"
                            }
                        }
                    },
                    "position" {
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
                    "date_start": "1990-01-01",
                    "date_end": "1990-01-01"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Positions History ID is required'}, status=400)
            positions_history = PositionsHistory.objects.filter(id__in=ids).select_related(
                'employee__member__address', 'employee__member__licence', 'position__licence',
                'position__coaching_licence'
            ).all()

            response_data = []
            for ph in positions_history:
                member = ph.employee.member
                address = member.address
                member_licence = member.licence
                position = ph.position
                position_licence = position.licence
                coaching_licence = position.coaching_licence

                member_data = {
                    "id": member.id,
                    "name": member.name,
                    "surname": member.surname,
                    "username": member.username,
                    "date_of_birth": str(member.date_of_birth),
                    "address": {
                        "id": address.id,
                        "country": address.country,
                        "city": address.city,
                        "street": address.street,
                        "street_no": address.street_no,
                        "postal_code": address.postal_code
                    },
                    "phone_number": member.phone_number,
                    "email": member.email,
                    "is_active": member.is_active,
                    "licence": {
                        "id": member_licence.id,
                        "licence_level": member_licence.licence_level
                    }
                }

                position_data = {
                    "id": position.id,
                    "name": position.name,
                    "salary_min": str(position.salary_min),
                    "salary_max": str(position.salary_max),
                    "licence": {
                        "id": position_licence.id,
                        "licence_level": position_licence.licence_level
                    },
                    "coaching_licence": {
                        "id": coaching_licence.id,
                        "licence_level": coaching_licence.licence_level
                    },
                    "speciality": position.speciality
                }

                response_data.append({
                    "employee": {
                        "id": ph.employee.id,
                        "member": member_data
                    },
                    "position": position_data,
                    "date_start": str(ph.date_start),
                    "date_end": str(ph.date_end) if ph.date_end else None
                })

            return JsonResponse({"positions_history": response_data}, status=200)
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
    def add_position_history(request):
        """
        Add new positions history.
        Example JSON request:
        {
            "positions_history": [
                {
                    "employee": {
                        "id": 1
                    },
                    "position": {
                        "id": 1
                    },
                    "date_start": "1990-01-01",
                    "date_end": "2010-01-01"
                },
                {
                    "employee": {
                        "id": 2
                    },
                    "position": {
                        "id": 3
                    },
                    "date_start": "1990-01-01",
                    "date_end": "2000-01-01"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Positions History added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            positions_history = data.get('positions_history', [])

            if not positions_history:
                return JsonResponse({'error': 'No positions history provided'}, status=400)

            position_ids = []
            with transaction.atomic():
                for position in positions_history:
                    employee_id = position.get('employee', {}).get('id')
                    position_id = position.get('position', {}).get('id')
                    date_start = position.get('date_start')
                    date_end = position.get('date_end')

                    if not date_start:
                        return JsonResponse({'error': 'Start date field is required'}, status=400)

                    if not Employees.objects.filter(id=employee_id).exists():
                        return JsonResponse({'error': f'Employee with ID {employee_id} does not exist'}, status=400)

                    if not Positions.objects.filter(id=position_id).exists():
                        return JsonResponse({'error': f'Position with ID {position_id} does not exist'}, status=400)

                    new_positions_history = PositionsHistory.objects.create(
                        employee_id=employee_id,
                        position_id=position_id,
                        date_start=date_start,
                        date_end=date_end
                    )
                    position_ids.append(new_positions_history.id)
            return JsonResponse({'message': 'Positions History added successfully', 'ids': position_ids}, status=201)
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
    def update_position_history(request):
        """
        Update positions by IDs.
        Example JSON request:
        {
            "positions_history": [
                {
                    "id": 1
                    "employee": {
                        "id": 1
                    },
                    "position": {
                        "id": 1
                    },
                    "date_start": "1990-01-01",
                    "date_end": "2010-01-01"
                },
                {
                    "id": 3
                    "employee": {
                        "id": 2
                    },
                    "position": {
                        "id": 3
                    },
                    "date_start": "1990-01-01",
                    "date_end": "2000-01-01"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Positions History added successfully",
            "ids": [1, 3]
        }
        """
        try:
            data = json.loads(request.body)
            positions_history = data.get('positions_history', [])

            if not positions_history:
                return JsonResponse({'error': 'No positions history provided'}, status=400)

            position_ids = []
            with transaction.atomic():
                for position in positions_history:
                    ph_id = position.get('id')
                    employee_id = position.get('employee', {}).get('id')
                    position_id = position.get('position', {}).get('id')
                    date_start = position.get('date_start')
                    date_end = position.get('date_end')

                    if not date_start:
                        return JsonResponse({'error': 'Start date field is required'}, status=400)

                    if not PositionsHistory.objects.filter(id=ph_id).exists():
                        return JsonResponse({'error': f'Position History with ID {ph_id} does not exist'}, status=400)

                    if not Employees.objects.filter(id=employee_id).exists():
                        return JsonResponse({'error': f'Employee with ID {employee_id} does not exist'}, status=400)

                    if not Positions.objects.filter(id=position_id).exists():
                        return JsonResponse({'error': f'Position with ID {position_id} does not exist'}, status=400)

                    PositionsHistory.objects.filter(id=ph_id).update(
                        employee_id=employee_id,
                        position_id=position_id,
                        date_start=date_start,
                        date_end=date_end
                    )
                    position_ids.append(ph_id)
            return JsonResponse({'message': 'Positions History added successfully', 'ids': position_ids}, status=201)
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
    def delete_positions_history(request):
        """
        Delete positions_history by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "message": "Positions History deleted successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Position History ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for position_id in ids:
                    PositionsHistory.objects.filter(id=position_id).delete()
                    deleted_ids.append(position_id)
            return JsonResponse({'message': 'Positions History deleted successfully', 'ids': deleted_ids}, status=200)
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
