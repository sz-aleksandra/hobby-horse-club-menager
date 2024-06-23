from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Employees, Members, Positions, Addresses, Licences
import json


class EmployeesView:
    @staticmethod
    @csrf_exempt
    def get_all_employees(request):
        """
        Get all employees.
        Example request JSON: N/A
        Example response JSON:
        {
            "employees": [
                {
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
                    },
                    "position": {
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
                    "salary": 1000,
                    "date_employed": "1985-05-15"
                }
            ]
        }
        """
        try:
            employees = Employees.objects.select_related(
                'member__address', 'member__licence', 'position__licence', 'position__coaching_licence'
            ).values(
                'id',
                'member_id', 'member__name', 'member__surname', 'member__username', 'member__date_of_birth',
                'member__address_id', 'member__address__country', 'member__address__city', 'member__address__street',
                'member__address__street_no', 'member__address__postal_code', 'member__phone_number', 'member__email',
                'member__is_active', 'member__licence_id', 'member__licence__licence_level',
                'position_id', 'position__name', 'position__salary_min', 'position__salary_max',
                'position__licence_id', 'position__licence__licence_level',
                'position__coaching_licence_id', 'position__coaching_licence__licence_level',
                'salary', 'date_employed'
            )

            data = []
            for employee in employees:
                employee_data = {
                    "id": employee['id'],
                    "member": {
                        "id": employee['member_id'],
                        "name": employee['member__name'],
                        "surname": employee['member__surname'],
                        "username": employee['member__username'],
                        "date_of_birth": employee['member__date_of_birth'],
                        "address": {
                            "id": employee['member__address_id'],
                            "country": employee['member__address__country'],
                            "city": employee['member__address__city'],
                            "street": employee['member__address__street'],
                            "street_no": employee['member__address__street_no'],
                            "postal_code": employee['member__address__postal_code']
                        },
                        "phone_number": employee['member__phone_number'],
                        "email": employee['member__email'],
                        "is_active": employee['member__is_active'],
                        "licence": {
                            "id": employee['member__licence_id'],
                            "licence_level": employee['member__licence__licence_level'],
                        }
                    },
                    "position": {
                        "id": employee['position_id'],
                        "name": employee['position__name'],
                        "salary_min": employee['position__salary_min'],
                        "salary_max": employee['position__salary_max'],
                        "licence": {
                            "id": employee['position__licence_id'],
                            "licence_level": employee['position__licence__licence_level']
                        },
                        "coaching_licence": {
                            "id": employee['position__coaching_licence_id'],
                            "licence_level": employee['position__coaching_licence__licence_level']
                        }
                    },
                    "salary": employee['salary'],
                    "date_employed": employee['date_employed']
                }
                data.append(employee_data)
            return JsonResponse({'employees': data}, status=200)
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
    def get_employee_by_id(request):
        """
        Example request JSON:
        {
            "ids": [1]
        }

        Example response JSON:
        {
            "employees": [
                {
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
                    },
                    "position": {
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
                    "salary": 1000,
                    "date_employed": "1985-05-15"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Employee ID is required'}, status=400)
            employees = Employees.objects.filter(id__in=ids).select_related(
                'member__address', 'member__licence', 'position__licence', 'position__coaching_licence'
            ).values(
                'id',
                'member_id', 'member__name', 'member__surname', 'member__username', 'member__date_of_birth',
                'member__address_id', 'member__address__country', 'member__address__city', 'member__address__street',
                'member__address__street_no', 'member__address__postal_code', 'member__phone_number', 'member__email',
                'member__is_active', 'member__licence_id', 'member__licence__licence_level',
                'position_id', 'position__name', 'position__salary_min', 'position__salary_max',
                'position__licence_id', 'position__licence__licence_level',
                'position__coaching_licence_id', 'position__coaching_licence__licence_level',
                'salary', 'date_employed'
            )

            data = []
            for employee in employees:
                employee_data = {
                    "id": employee['id'],
                    "member": {
                        "id": employee['member_id'],
                        "name": employee['member__name'],
                        "surname": employee['member__surname'],
                        "username": employee['member__username'],
                        "date_of_birth": employee['member__date_of_birth'],
                        "address": {
                            "id": employee['member__address_id'],
                            "country": employee['member__address__country'],
                            "city": employee['member__address__city'],
                            "street": employee['member__address__street'],
                            "street_no": employee['member__address__street_no'],
                            "postal_code": employee['member__address__postal_code']
                        },
                        "phone_number": employee['member__phone_number'],
                        "email": employee['member__email'],
                        "is_active": employee['member__is_active'],
                        "licence": {
                            "id": employee['member__licence_id'],
                            "licence_level": employee['member__licence__licence_level'],
                        }
                    },
                    "position": {
                        "id": employee['position_id'],
                        "name": employee['position__name'],
                        "salary_min": employee['position__salary_min'],
                        "salary_max": employee['position__salary_max'],
                        "licence": {
                            "id": employee['position__licence_id'],
                            "licence_level": employee['position__licence__licence_level']
                        },
                        "coaching_licence": {
                            "id": employee['position__coaching_licence_id'],
                            "licence_level": employee['position__coaching_licence__licence_level']
                        }
                    },
                    "salary": employee['salary'],
                    "date_employed": employee['date_employed']
                }
                data.append(employee_data)
            return JsonResponse({'employees': data}, status=200)
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
    def add_employee(request):
        """
        Example request JSON:
        {
            "employees": [
                {
                    "member": {
                        "name": "John",
                        "surname": "Doe",
                        "username": "johndoe",
                        "password": "qwerty",
                        "date_of_birth": "1990-01-01",
                        "address": {
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
                        }
                    },
                    "position": {
                        "id": 1,
                    },
                    "salary": 1000,
                    "date_employed": "1985-05-15"
                }
            ]
        }

        Example response JSON:
        {
            "message": "Employees added successfully",
            "ids": [3]
        }
        """
        try:
            data = json.loads(request.body)
            employees = data.get('employees', [])

            if not employees:
                return JsonResponse({'error': 'No employees provided'}, status=400)

            new_employee_ids = []
            with transaction.atomic():
                for employee_data in employees:
                    member_data = employee_data.pop('member', None)
                    address_data = member_data.pop('address', None)
                    licence_id = member_data.get('licence', {}).get('id')
                    position_id = employee_data.get('position', {}).get('id')
                    salary = employee_data.get('salary')
                    date_employed = employee_data.get('date_employed')

                    # Check if Licence ID exists
                    if not Licences.objects.filter(id=licence_id).exists():
                        return JsonResponse({'error': f'Licence with ID {licence_id} does not exist'}, status=400)

                    if not Positions.objects.filter(id=position_id).exists():
                        return JsonResponse({'error': f'Position with ID {position_id} does not exist'}, status=400)

                    # Create Address object
                    address = Addresses.objects.create(**address_data)
                    member_data['address_id'] = address.id

                    # Create Member object
                    new_member = Members.objects.create(name=member_data['name'], surname=member_data['surname'],
                                                        username=member_data['username'],
                                                        password=member_data['password'],
                                                        date_of_birth=member_data['date_of_birth'],
                                                        address_id=address.id,
                                                        phone_number=member_data['phone_number'],
                                                        email=member_data['email'],
                                                        is_active=member_data['is_active'], licence_id=licence_id)

                    # Create Employee object
                    new_employee = Employees.objects.create(member_id=new_member.id, position_id=position_id,
                                                            salary=salary, date_employed=date_employed)
                    new_employee_ids.append(new_employee.id)

            return JsonResponse({'message': 'Employees added successfully', 'ids': new_employee_ids}, status=200)
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
    def update_employee(request):
        """
        Example request JSON:
        {
            "employees": [
                {
                    "id": 1,
                    "member": {
                        "name": "John",
                        "surname": "Doe",
                        "username": "johndoe",
                        "password": "qwerty",
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
                        }
                    },
                    "position": {
                        "id": 1,
                    },
                    "salary": 1000,
                    "date_employed": "1985-05-15"
                }
            ]
        }

        Example response JSON:
        {
            "message": "Employees updated successfully",
            "ids": [1]
        }
        """
        try:
            data = json.loads(request.body)
            employees = data.get('employees', [])

            if not employees:
                return JsonResponse({'error': 'No employees provided'}, status=400)

            updated_ids = []
            with transaction.atomic():
                for employee_data in employees:
                    member_data = employee_data.pop('member', None)
                    address_data = member_data.pop('address', None)
                    licence_id = member_data.get('licence', {}).get('id')
                    position_id = employee_data.get('position', {}).get('id')
                    salary = employee_data.get('salary')
                    date_employed = employee_data.get('date_employed')

                    # Check if Licence ID exists
                    if not Licences.objects.filter(id=licence_id).exists():
                        return JsonResponse({'error': f'Licence with ID {licence_id} does not exist'}, status=400)

                    if not Positions.objects.filter(id=position_id).exists():
                        return JsonResponse({'error': f'Position with ID {position_id} does not exist'}, status=400)

                    if not Addresses.objects.filter(id=address_data.get('id')).exists():
                        return JsonResponse({'error': f"Address with ID {address_data.get('id')} does not exist"},
                                            status=400)

                    if not Employees.objects.filter(id=employee_data.get('id')).exists():
                        return JsonResponse({'error': f"Employee with ID {employee_data.get('id')} does not exist"},
                                            status=400)

                    # Update Address
                    Addresses.objects.filter(id=address_data.get('id')).update(**address_data)
                    member_data['address_id'] = address_data.get('id')

                    # Update Member
                    Members.objects.filter(id=member_data.get('id')).update(
                        name=member_data['name'], surname=member_data['surname'],
                        username=member_data['username'],
                        password=member_data['password'],
                        date_of_birth=member_data['date_of_birth'],
                        address_id=address_data.get('id'),
                        phone_number=member_data['phone_number'],
                        email=member_data['email'],
                        is_active=member_data['is_active'], licence_id=licence_id)

                    # Update Employee
                    Employees.objects.filter(id=employee_data.get('id')).update(member_id=member_data.get('id'),
                                                                                position_id=position_id,
                                                                                salary=salary,
                                                                                date_employed=date_employed)
                    updated_ids.append(employee_data.get('id'))

            return JsonResponse({'message': 'Employees updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_employee(request):
        """
        Delete employees by IDs.
        Example JSON request:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "message": "Employees deleted successfully",
            "deleted_ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Employee ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for employee_id in ids:
                    Employees.objects.filter(id=employee_id).delete()
                    deleted_ids.append(employee_id)

            return JsonResponse({'message': 'Employees deleted successfully', 'deleted_ids': deleted_ids}, status=200)
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
    def login_employee(request):
        """
        Login as employee
        Example JSON request:
        {
            "username": login,
            "password": password
        }
        Example JSON response:
        {
            "id": 2
        }
        """
        try:
            data = json.loads(request.body)
            login = data.get('login')
            password = data.get('password')

            if not login or not password:
                return JsonResponse({'error': 'No login or password provided'}, status=400)

            try:
                employee = Employees.objects.get(member__username=login, member__is_active=True)
            except Employees.DoesNotExist:
                return JsonResponse({'error': 'Invalid login'}, status=401)

            if not password == employee.member.password:
                return JsonResponse({'error': 'Invalid password'}, status=401)

            return JsonResponse({'id': employee.id}, status=200)
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
    def login_owner(request):
        """
        Login as owner
        Example JSON request:
        {
            "username": login,
            "password": password
        }
        Example JSON response:
        {
            "id": 2
        }
        """
        try:
            data = json.loads(request.body)
            login = data.get('login')
            password = data.get('password')

            if not login or not password:
                return JsonResponse({'error': 'No login or password provided'}, status=400)

            try:
                employee = Employees.objects.get(member__username=login, position__name='Owner', member__is_active=True)
            except Employees.DoesNotExist:
                return JsonResponse({'error': 'Invalid login'}, status=401)

            if not password == employee.member.password:
                return JsonResponse({'error': 'Invalid password'}, status=401)

            return JsonResponse({'id': employee.id}, status=200)
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
    def deactivate_account(request):
        """
        Example JSON request:
        {
            "id": 1
        }
        Example JSON response:
        {
            'message': 'Successfully deactivated account',
            'id': 1
        }
        """
        try:
            data = json.loads(request.body)
            employee_id = data.get('id')

            if not employee_id:
                return JsonResponse({'error': 'No id provided'}, status=400)
            try:
                employee = Employees.objects.get(id=employee_id)
            except Employees.DoesNotExist:
                return JsonResponse({'error': 'Invalid employee id'}, status=401)

            employee.member.is_active = False
            employee.member.save()
            return JsonResponse({'message': 'Successfully deactivated account', 'id': employee.id}, status=200)

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
