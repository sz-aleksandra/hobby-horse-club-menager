from django.http import JsonResponse
from horses_database.models import Addresses
import json
from django.db import transaction, IntegrityError, DatabaseError
from django.views.decorators.csrf import csrf_exempt


class AddressesView:
    @csrf_exempt
    @staticmethod
    def get_all_addresses(request):
        """
        Example JSON payload (not needed for GET request):
        N/A

        Example JSON response:
        {
            "addresses": [
                {
                    "id": 1,
                    "country": "Poland",
                    "city": "Warsaw",
                    "street": "Puławska 20"
                },
                {
                    "id": 2,
                    "country": "Poland",
                    "city": "Kraków",
                    "street": "Rynek Główny 10"
                },
                {
                    "id": 3,
                    "country": "USA",
                    "city": "New York",
                    "street": "Broadway"
                }
            ]
        }
        """
        addresses = list(Addresses.objects.all().values())
        return JsonResponse({'addresses': addresses}, status=200)


    @csrf_exempt
    @staticmethod
    def get_address_by_id(request):
        """
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example JSON response:
        {
            "addresses": [
                {
                    "id": 1,
                    "country": "Poland",
                    "city": "Warsaw",
                    "street": "Puławska 20"
                },
                {
                    "id": 2,
                    "country": "Poland",
                    "city": "Kraków",
                    "street": "Rynek Główny 10"
                }
            ]
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

        Example JSON response:
        {
            "addresses": [
                {
                    "id": 1,
                    "country": "Poland",
                    "city": "Warsaw",
                    "street": "Puławska 20"
                },
                {
                    "id": 2,
                    "country": "Poland",
                    "city": "Kraków",
                    "street": "Rynek Główny 10"
                },
                {
                    "id": 3,
                    "country": "USA",
                    "city": "New York",
                    "street": "Broadway"
                }
            ]
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

    @csrf_exempt
    @staticmethod
    def get_all_addresses_in_city(request):
        """
        Retrieve all addresses in a city.

        Example JSON payload:
        {
            "city": "Warsaw"
        }

        Example JSON response:
        {
          "addresses": [
            {
              "id": 1,
              "country": "Poland",
              "city": "Warsaw",
              "street": "Puławska 20",
              "street_no": "",
              "postal_code": ""
            },
            {
              "id": 2,
              "country": "Poland",
              "city": "Warsaw",
              "street": "Marszałkowska 45",
              "street_no": "",
              "postal_code": ""
            },
            {
              "id": 3,
              "country": "Poland",
              "city": "Warsaw",
              "street": "Krakowskie Przedmieście 56",
              "street_no": "",
              "postal_code": ""
            }
          ]
        }
        """
        try:
            data = json.loads(request.body)
            city = data.get('city')

            if not city:
                return JsonResponse({'error': 'City is required'}, status=400)

            addresses = list(Addresses.objects.filter(city=city).values())
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

    @csrf_exempt
    @staticmethod
    def get_all_addresses_in_street(request):
        """
        Retrieve all addresses on a street.

        Example JSON payload:
        {
            "street": "Krakowskie Przedmieście"
        }

        Example JSON response:
        {
          "addresses": [
            {
              "id": 3,
              "country": "Poland",
              "city": "Warsaw",
              "street": "Krakowskie Przedmieście",
              "street_no": "56",
              "postal_code": ""
            }
          ]
        }
        """
        try:
            data = json.loads(request.body)
            street = data.get('street')

            if not street:
                return JsonResponse({'error': 'Street is required'}, status=400)

            addresses = list(Addresses.objects.filter(street=street).values())
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
                    "street": "Krakowskie Przedmieście 56",
                    "street_no": "56",
                    "postal_code": "00-927"
                },
                {
                    "country": "USA",
                    "city": "New York",
                    "street": "Broadway",
                    "street_no": "123",
                    "postal_code": "10036"
                }
            ]
        }

        Example response:
        {
            "message": "Addresses added successfully",
            "added_ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            addresses = data.get('addresses', [])
            addresses_id = []
            with transaction.atomic():
                for addr in addresses:
                    country = addr['country']
                    city = addr['city']
                    street = addr['street']
                    street_no = addr['street_no']
                    postal_code = addr['postal_code']
                    if not country or not city or not street or not street_no or not postal_code:
                        return JsonResponse({'error': 'Country, city, street, street_no, and postal_code are required fields'}, status=400)
                    address = Addresses.objects.create(
                        country=country,
                        city=city,
                        street=street,
                        street_no=street_no,
                        postal_code=postal_code
                    )
                    addresses_id.append(address.id)
            return JsonResponse({'message': 'Addresses added successfully', 'added_ids': addresses_id}, status=201)

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
    def delete_addresses(request):
        """
        Delete addresses by IDs.

        Example JSON payload:
        {
            "ids": [1, 2, 3]
        }

        Example response:
        {
            "message": "Addresses deleted successfully",
            "deleted_ids": [1, 2, 3]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            deleted_ids = []
            if not ids:
                return JsonResponse({'error': 'At least one Address ID is required'}, status=400)

            with transaction.atomic():
                address = Addresses.objects.filter(id__in=ids).delete()
                deleted_ids.append(address.id)

            return JsonResponse({'message': 'Addresses deleted successfully', 'deleted_ids': deleted_ids}, status=200)

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
    def update_addresses(request):
        """
        Update addresses by IDs.

        Example JSON payload:
        {
            "addresses": [
                {
                    "id": 1,
                    "country": "Poland",
                    "city": "Warsaw",
                    "street": "Nowy Świat",
                    "street_no": "15",
                    "postal_code": "00-029"
                },
                {
                    "id": 2,
                    "country": "USA",
                    "city": "Los Angeles",
                    "street": "Sunset Blvd",
                    "street_no": "1234",
                    "postal_code": "90069"
                }
            ]
        }

        Example response:
        {
            "message": "Addresses updated successfully",
            "updated_ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            addresses = data.get('addresses', [])
            addresses_ids = []

            with transaction.atomic():
                for addr in addresses:
                    address_id = addr.get('id')
                    addresses_ids.append(address_id)
                    if not address_id:
                        return JsonResponse({'error': 'Address ID is required for updating'}, status=400)

                    address = Addresses.objects.get(id=address_id)
                    address.country = addr.get('country', address.country)
                    address.city = addr.get('city', address.city)
                    address.street = addr.get('street', address.street)
                    address.street_no = addr.get('street_no', address.street_no)
                    address.postal_code = addr.get('postal_code', address.postal_code)
                    address.save()

            return JsonResponse({'message': 'Addresses updated successfully', 'updated_ids': addresses_ids}, status=200)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except Addresses.DoesNotExist:
            return JsonResponse({'error': 'Address not found'}, status=404)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)
