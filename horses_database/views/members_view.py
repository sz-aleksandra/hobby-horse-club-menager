from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Members, Addresses, Licences
import json


class MembersView:
    @staticmethod
    @csrf_exempt
    def get_all_members(request):
        """
        Get all members.
        Example response JSON:
        {
            "members": [
                {
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
                        "licence_level": "Advanced"
                    }
                },
                {
                    "id": 2,
                    "name": "Jane",
                    "surname": "Smith",
                    "username": "janesmith",
                    "date_of_birth": "1985-05-15",
                    "address": {
                        "id": 2,
                        "country": "Canada",
                        "city": "Toronto",
                        "street": "King Street",
                        "street_no": "456",
                        "postal_code": "M5V 1J4"
                    },
                    "phone_number": "+9876543210",
                    "email": "jane.smith@example.com",
                    "is_active": true,
                    "licence": {
                        "id": 2,
                        "licence_level": "Basic"
                    }
                }
            ]
        }
        """
        try:
            members = Members.objects.select_related('address', 'licence').values(
                'id', 'name', 'surname', 'username', 'date_of_birth',
                'address__id', 'address__country', 'address__city', 'address__street',
                'address__street_no', 'address__postal_code', 'phone_number', 'email',
                'is_active', 'licence__id', 'licence__licence_level'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "username": member['username'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street'],
                        "street_no": member['address__street_no'],
                        "postal_code": member['address__postal_code']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active'],
                    "licence": {
                        "id": member['licence__id'],
                        "licence_level": member['licence__licence_level']
                    }
                }
                for member in members
            ]
            return JsonResponse({'members': data}, status=200)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_member_by_id(request):
        """
        Get members by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "members": [
                {
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
                        "licence_level": "Advanced"
                    }
                },
                {
                    "id": 2,
                    "name": "Jane",
                    "surname": "Smith",
                    "username": "janesmith",
                    "date_of_birth": "1985-05-15",
                    "address": {
                        "id": 2,
                        "country": "Canada",
                        "city": "Toronto",
                        "street": "King Street",
                        "street_no": "456",
                        "postal_code": "M5V 1J4"
                    },
                    "phone_number": "+9876543210",
                    "email": "jane.smith@example.com",
                    "is_active": true,
                    "licence": {
                        "id": 2,
                        "licence_level": "Basic"
                    }
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Member ID is required'}, status=400)

            members = Members.objects.filter(id__in=ids).select_related('address', 'licence').values(
                'id', 'name', 'surname', 'username', 'date_of_birth',
                'address__id', 'address__country', 'address__city', 'address__street',
                'address__street_no', 'address__postal_code', 'phone_number', 'email',
                'is_active', 'licence__id', 'licence__licence_level'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "username": member['username'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street'],
                        "street_no": member['address__street_no'],
                        "postal_code": member['address__postal_code']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active'],
                    "licence": {
                        "id": member['licence__id'],
                        "licence_level": member['licence__licence_level']
                    }
                }
                for member in members
            ]
            return JsonResponse({'members': data}, status=200)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_member(request):
        """
        Add new members.
        Example JSON payload:
        {
            "members": [
                {
                    "name": "John",
                    "surname": "Doe",
                    "username": "johndoe",
                    "password": "secure_password",
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
                        "id": 1  # Existing Licence ID
                    }
                },
                {
                    "name": "Jane",
                    "surname": "Smith",
                    "username": "janesmith",
                    "password": "strong_password",
                    "date_of_birth": "1985-05-15",
                    "address": {
                        "country": "Canada",
                        "city": "Toronto",
                        "street": "King Street",
                        "street_no": "456",
                        "postal_code": "M5V 1J4"
                    },
                    "phone_number": "+9876543210",
                    "email": "jane.smith@example.com",
                    "is_active": true,
                    "licence": {
                        "id": 2  # Existing Licence ID
                    }
                }
            ]
        }
        Example response JSON:
        {
            "message": "Members added successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            members = data.get('members', [])
            new_member_ids = []
            with transaction.atomic():
                for member_data in members:
                    address_data = member_data.pop('address', None)
                    licence_id = member_data.get('licence', {}).get('id')

                    # Check if Licence ID exists
                    if not Licences.objects.filter(id=licence_id).exists():
                        return JsonResponse({'error': f'Licence with ID {licence_id} does not exist'}, status=400)

                    # Create Address object
                    if address_data:
                        address = Addresses.objects.create(**address_data)
                        member_data['address_id'] = address.id

                    # Create Member object
                    new_member = Members.objects.create(licence_id=licence_id, **member_data)
                    new_member_ids.append(new_member.id)

            return JsonResponse({'message': 'Members added successfully', 'ids': new_member_ids}, status=200)
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
    def update_member(request):
        """
        Update members by IDs.
        Example JSON payload:
        {
            "members": [
                {
                    "id": 1,
                    "name": "Updated John",
                    "surname": "Doe",
                    "username": "johndoe",
                    "password": "updated_secure_password",
                    "date_of_birth": "1990-01-01",
                    "address": {
                        "id": 1,  # Existing Address ID
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
                        "id": 2  # Existing Licence ID
                    }
                },
                {
                    "id": 2,
                    "name": "Updated Jane",
                    "surname": "Smith",
                    "username": "janesmith",
                    "password": "updated_strong_password",
                    "date_of_birth": "1985-05-15",
                    "address": {
                        "id": 2,  # Existing Address ID
                        "country": "Canada",
                        "city": "Toronto",
                        "street": "King Street",
                        "street_no": "456",
                        "postal_code": "M5V 1J4"
                    },
                    "phone_number": "+9876543210",
                    "email": "jane.smith@example.com",
                    "is_active": true,
                    "licence": {
                        "id": 1  # Existing Licence ID
                    }
                }
            ]
        }
        Example response JSON:
        {
            "message": "Members updated successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            members = data.get('members', [])
            updated_ids = []
            with transaction.atomic():
                for member_data in members:
                    member_id = member_data.get('id')
                    address_data = member_data.get('address')
                    licence_id = member_data.get('licence', {}).get('id')

                    # Check if Member and Licence IDs exist
                    if not Members.objects.filter(id=member_id).exists():
                        return JsonResponse({'error': f'Member with ID {member_id} does not exist'}, status=400)
                    if not Licences.objects.filter(id=licence_id).exists():
                        return JsonResponse({'error': f'Licence with ID {licence_id} does not exist'}, status=400)

                    # Update Address if provided
                    if address_data:
                        address_id = address_data.pop('id', None)
                        if address_id:
                            Addresses.objects.filter(id=address_id).update(**address_data)
                        else:
                            address = Addresses.objects.create(**address_data)
                            address_id = address.id

                    # Update Member
                    Members.objects.filter(id=member_id).update(
                        name=member_data.get('name'),
                        surname=member_data.get('surname'),
                        username=member_data.get('username'),
                        date_of_birth=member_data.get('date_of_birth'),
                        phone_number=member_data.get('phone_number'),
                        email=member_data.get('email'),
                        is_active=member_data.get('is_active'),
                        licence_id=licence_id,
                        address_id=address_id
                    )
                    updated_ids.append(member_id)

            return JsonResponse({'message': 'Members updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_member(request):
        """
        Delete members by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "message": "Members deleted successfully",
            "deleted_ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Member ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for member_id in ids:
                    Members.objects.filter(id=member_id).delete()
                    deleted_ids.append(member_id)

            return JsonResponse({'message': 'Members deleted successfully', 'deleted_ids': deleted_ids}, status=200)
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
