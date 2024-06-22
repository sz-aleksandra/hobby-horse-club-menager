# from django.shortcuts import render
from django.http import JsonResponse
from horses_database.models import *
import json
from django.db import transaction, IntegrityError, DatabaseError
from django.views.decorators.csrf import csrf_exempt
from django.core import serializers
# Create your views here.
class AddressesView:
    @csrf_exempt
    @staticmethod
    def get_all_addresses(request):
        # Retrieve all addresses
        addresses = list(Addresses.objects.all().values())
        return JsonResponse({'addresses': addresses}, status=200)

    @staticmethod
    @csrf_exempt
    def add_addresses(request):
        """
        Add addresses.
        Example JSON payload:
        {
            "addresses": [
                {
                    "country": "Poland",
                    "city": "Warsaw",
                    "street": "Krakowskie Przedmieście 56"
                },
                {
                    "country": "USA",
                    "city": "New York",
                    "street": "Broadway 123"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            addresses = data.get('addresses', [])
            with transaction.atomic():
                for addr in addresses:
                    country = addr['country']
                    city = addr['city']
                    street = addr['street']
                    if not country or not city or not street:
                        return JsonResponse({'error': 'Country, city, and street are required fields'}, status=400)
                    Addresses.objects.create(
                        country=country,
                        city=city,
                        street=street
                    )
            return JsonResponse({'message': 'Addresses added successfully'}, status=201)

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

    @csrf_exempt
    @staticmethod
    def get_address_by_id(request):
        """
        Retrieve addresses by IDs.
        Example JSON payload:
        {
            "ids": [1, 2, 3]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Address ID is required'}, status=400)

            addresses = list(Addresses.objects.filter(id__in=ids).values())
            return JsonResponse({'addresses': addresses}, status=200)

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

    @csrf_exempt
    @staticmethod
    def get_all_addresses_in_country(request):
        """
        Retrieve all addresses in multiple countries.
        Example JSON payload:
        {
            "countries": ["Poland", "USA"]
        }
        """
        try:
            data = json.loads(request.body)
            countries = data.get('countries', [])

            if not countries:
                return JsonResponse({'error': 'At least one country is required'}, status=400)

            addresses = list(Addresses.objects.filter(country__in=countries).values())
            data = {"addresses": addresses}
            return JsonResponse(data, status=200)

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

class MembersView:
    @csrf_exempt
    @staticmethod
    def get_all_members(request):
        """
        Retrieve all members.
        """
        members = Members.objects.select_related('address').values(
            'id', 'name', 'surname', 'date_of_birth', 'address__id', 'address__country', 'address__city',
            'address__street', 'phone_number', 'email', 'is_active'
        )

        data = [
            {
                "id": member['id'],
                "name": member['name'],
                "surname": member['surname'],
                "date_of_birth": member['date_of_birth'],
                "address": {
                    "id": member['address__id'],
                    "country": member['address__country'],
                    "city": member['address__city'],
                    "street": member['address__street']
                },
                "phone_number": member['phone_number'],
                "email": member['email'],
                "is_active": member['is_active']
            }
            for member in members
        ]
        return JsonResponse({'members': data}, status=200)

    @csrf_exempt
    @staticmethod
    def get_members_by_id(request):
        """
        Retrieve members by IDs.
        Example JSON payload:
        {
            "ids": [1, 2, 3]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])

            if not ids:
                return JsonResponse({'error': 'At least one Member ID is required'}, status=400)

            members = Members.objects.filter(id__in=ids).select_related('address').values(
                'id', 'name', 'surname', 'date_of_birth', 'address__id', 'address__country', 'address__city',
                'address__street', 'phone_number', 'email', 'is_active'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active']
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

    @csrf_exempt
    @staticmethod
    def add_members(request):
        """
        Add members and their addresses.
        Example JSON payload:
        {
            "members": [
                {
                    "name": "John",
                    "surname": "Doe",
                    "date_of_birth": "1990-01-01",
                    "address": {
                        "country": "Poland",
                        "city": "Warsaw",
                        "street": "Krakowskie Przedmieście 56"
                    },
                    "phone_number": "+1234567890",
                    "email": "john.doe@example.com",
                    "is_active": true
                },
                {
                    "name": "Jane",
                    "surname": "Smith",
                    "date_of_birth": "1995-02-15",
                    "address": {
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway 123"
                    },
                    "phone_number": "+1987654321",
                    "email": "jane.smith@example.com",
                    "is_active": false
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            members_data = data.get('members', [])

            with transaction.atomic():
                for member_data in members_data:
                    # Extract member data
                    name = member_data['name']
                    surname = member_data['surname']
                    date_of_birth = member_data['date_of_birth']
                    phone_number = member_data['phone_number']
                    email = member_data['email']
                    is_active = member_data['is_active']

                    # Extract address data
                    address_data = member_data.get('address', {})
                    country = address_data.get('country')
                    city = address_data.get('city')
                    street = address_data.get('street')

                    # Validate required fields
                    if not country or not city or not street:
                        return JsonResponse({'error': 'Country, city, and street are required fields'}, status=400)

                    # Create Address object
                    address = Addresses.objects.create(
                        country=country,
                        city=city,
                        street=street
                    )

                    # Create Member object
                    Member.objects.create(
                        name=name,
                        surname=surname,
                        date_of_birth=date_of_birth,
                        address=address,
                        phone_number=phone_number,
                        email=email,
                        is_active=is_active
                    )

            return JsonResponse({'message': 'Members and addresses added successfully'}, status=201)

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

    @csrf_exempt
    @staticmethod
    def get_all_members_in_country(request):
        """
        Retrieve all members in multiple countries.
        Example JSON payload:
        {
            "countries": ["Poland", "USA"]
        }
        """
        try:
            data = json.loads(request.body)
            countries = data.get('countries', [])
            if not countries:
                return JsonResponse({'error': 'At least one country is required'}, status=400)

            addresses = Addresses.objects.filter(country__in=countries)
            members = Members.objects.filter(address__in=addresses).select_related('address').values(
                'id', 'name', 'surname', 'date_of_birth', 'address__id', 'address__country', 'address__city',
                'address__street', 'phone_number', 'email', 'is_active'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active']
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

    @csrf_exempt
    @staticmethod
    def get_all_members_by_address_id(request):
        """
        Retrieve all members by Address ID.
        Example JSON payload:
        {
            "address_id": 1
        }
        """
        try:
            data = json.loads(request.body)
            address_id = data.get('address_id')

            if not address_id:
                return JsonResponse({'error': 'Address ID is required'}, status=400)

            members = Members.objects.filter(address_id=address_id).select_related('address').values(
                'id', 'name', 'surname', 'date_of_birth', 'address__id', 'address__country', 'address__city',
                'address__street', 'phone_number', 'email', 'is_active'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active']
                }
                for member in members
            ]
            return JsonResponse({'members': data}, status=200)

        except Addresses.DoesNotExist:
            return JsonResponse({"error": "Address not found"}, status=404)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @csrf_exempt
    @staticmethod
    def get_all_active_members(request):
        """
        Retrieve all active members.
        """
        try:
            members = Members.objects.filter(is_active=True).select_related('address').values(
                'id', 'name', 'surname', 'date_of_birth', 'address__id', 'address__country', 'address__city',
                'address__street', 'phone_number', 'email', 'is_active'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active']
                }
                for member in members
            ]
            return JsonResponse({'members': data}, status=200)

        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @csrf_exempt
    @staticmethod
    def get_all_inactive_members(request):
        """
        Retrieve all inactive members.
        """
        try:
            members = Members.objects.filter(is_active=False).select_related('address').values(
                'id', 'name', 'surname', 'date_of_birth', 'address__id', 'address__country', 'address__city',
                'address__street', 'phone_number', 'email', 'is_active'
            )

            data = [
                {
                    "id": member['id'],
                    "name": member['name'],
                    "surname": member['surname'],
                    "date_of_birth": member['date_of_birth'],
                    "address": {
                        "id": member['address__id'],
                        "country": member['address__country'],
                        "city": member['address__city'],
                        "street": member['address__street']
                    },
                    "phone_number": member['phone_number'],
                    "email": member['email'],
                    "is_active": member['is_active']
                }
                for member in members
            ]
            return JsonResponse({'members': data}, status=200)

        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

class AccessoriesView:
    @csrf_exempt
    @staticmethod
    def get_all_accessories(request):
        """
        Retrieve all accessories.
        """
        try:
            accessories = list(Accessories.objects.all().values())
            return JsonResponse({'accessories': accessories}, status=200)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @csrf_exempt
    @staticmethod
    def add_new_accessory(request):
        """
        Add a new accessory.
        Example JSON payload:
        {
            "name": "Headphones"
        }
        """
        try:
            data = json.loads(request.body)
            name = data.get('name')

            if not name:
                return JsonResponse({'error': 'Name is required'}, status=400)

            accessory = Accessories.objects.create(name=name)
            return JsonResponse({'message': 'Accessory added successfully', 'id': accessory.id}, status=201)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)
