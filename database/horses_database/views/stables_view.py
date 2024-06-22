from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Stables, Addresses
import json


class StablesView:
    @staticmethod
    @csrf_exempt
    def get_all_stables(request):
        """
        Get all stables.
        Example response JSON:
        {
            "stables": [
                {
                    "id": 1,
                    "name": "Green Pastures Stables",
                    "address": {
                        "id": 1,
                        "country": "United States",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    }
                },
                {
                    "id": 2,
                    "name": "Golden Fields Equestrian Center",
                    "address": {
                        "id": 2,
                        "country": "United States",
                        "city": "Los Angeles",
                        "street": "Sunset Blvd",
                        "street_no": "456",
                        "postal_code": "90001"
                    }
                }
            ]
        }
        """
        try:
            stables = Stables.objects.select_related('address').values(
                'id', 'name', 'address__id', 'address__country', 'address__city',
                'address__street', 'address__street_no', 'address__postal_code'
            )

            data = [
                {
                    "id": stable['id'],
                    "name": stable['name'],
                    "address": {
                        "id": stable['address__id'],
                        "country": stable['address__country'],
                        "city": stable['address__city'],
                        "street": stable['address__street'],
                        "street_no": stable['address__street_no'],
                        "postal_code": stable['address__postal_code']
                    }
                }
                for stable in stables
            ]
            return JsonResponse({'stables': data}, status=200)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_stable_by_id(request):
        """
        Get stables by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "stables": [
                {
                    "id": 1,
                    "name": "Green Pastures Stables",
                    "address": {
                        "id": 1,
                        "country": "United States",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    }
                },
                {
                    "id": 2,
                    "name": "Golden Fields Equestrian Center",
                    "address": {
                        "id": 2,
                        "country": "United States",
                        "city": "Los Angeles",
                        "street": "Sunset Blvd",
                        "street_no": "456",
                        "postal_code": "90001"
                    }
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Stable ID is required'}, status=400)

            stables = Stables.objects.filter(id__in=ids).select_related('address').values(
                'id', 'name', 'address__id', 'address__country', 'address__city',
                'address__street', 'address__street_no', 'address__postal_code'
            )

            data = [
                {
                    "id": stable['id'],
                    "name": stable['name'],
                    "address": {
                        "id": stable['address__id'],
                        "country": stable['address__country'],
                        "city": stable['address__city'],
                        "street": stable['address__street'],
                        "street_no": stable['address__street_no'],
                        "postal_code": stable['address__postal_code']
                    }
                }
                for stable in stables
            ]
            return JsonResponse({'stables': data}, status=200)
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except KeyError as e:
            return JsonResponse({'error': f'Missing field in JSON: {str(e)}'}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_stable(request):
        """
        Add new stables.
        Example JSON payload:
        {
            "stables": [
                {
                    "name": "Green Pastures Stables",
                    "address": {
                        "country": "United States",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    }
                },
                {
                    "name": "Golden Fields Equestrian Center",
                    "address": {
                        "country": "United States",
                        "city": "Los Angeles",
                        "street": "Sunset Blvd",
                        "street_no": "456",
                        "postal_code": "90001"
                    }
                }
            ]
        }
        Example response JSON:
        {
            "message": "Stables added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            stables = data.get('stables', [])
            stable_ids = []
            with transaction.atomic():
                for stable in stables:
                    name = stable.get('name')
                    address_data = stable.get('address', {})
                    country = address_data.get('country')
                    city = address_data.get('city')
                    street = address_data.get('street')
                    street_no = address_data.get('street_no')
                    postal_code = address_data.get('postal_code')

                    if not name or not country or not city or not street or not street_no or not postal_code:
                        return JsonResponse(
                            {'error': 'All fields (name, country, city, street, street_no, postal_code) are required'},
                            status=400)

                    address = Addresses.objects.create(
                        country=country,
                        city=city,
                        street=street,
                        street_no=street_no,
                        postal_code=postal_code
                    )

                    new_stable = Stables.objects.create(
                        name=name,
                        address=address
                    )
                    stable_ids.append(new_stable.id)
            return JsonResponse({'message': 'Stables added successfully', 'ids': stable_ids}, status=201)
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
    def update_stable(request):
        """
        Update stables by IDs.
        Example JSON payload:
        {
            "stables": [
                {
                    "id": 4,
                    "name": "Updated Green Pastures Stables",
                    "address": {
                        "id": 1,
                        "country": "United States",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    }
                },
                {
                    "id": 5,
                    "name": "Updated Golden Fields Equestrian Center",
                    "address": {
                        "id": 2,
                        "country": "United States",
                        "city": "Los Angeles",
                        "street": "Sunset Blvd",
                        "street_no": "456",
                        "postal_code": "90001"
                    }
                }
            ]
        }
        Example response JSON:
        {
            "message": "Stables updated successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            stables = data.get('stables', [])
            updated_ids = []
            with transaction.atomic():
                for stable in stables:
                    stable_id = stable.get('id')
                    name = stable.get('name')
                    address_data = stable.get('address', {})
                    address_id = address_data.get('id')
                    country = address_data.get('country')
                    city = address_data.get('city')
                    street = address_data.get('street')
                    street_no = address_data.get('street_no')
                    postal_code = address_data.get('postal_code')

                    if not stable_id or not name or not address_id or not country or not city or not street or not street_no or not postal_code:
                        return JsonResponse(
                            {'error': 'ID, name, address_id, country, city, street, street_no, postal_code are required fields'},
                            status=400)

                    address, created = Addresses.objects.update_or_create(
                        id=address_id,
                        defaults={
                            'country': country,
                            'city': city,
                            'street': street,
                            'street_no': street_no,
                            'postal_code': postal_code
                        }
                    )

                    Stables.objects.filter(id=stable_id).update(
                        name=name,
                        address=address
                    )
                    updated_ids.append(stable_id)
            return JsonResponse({'message': 'Stables updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_stable(request):
        """
        Delete stables by IDs.
        Example JSON payload:
        {
            "ids": [4, 5]
        }
        Example response JSON:
        {
            "message": "Stables deleted successfully",
            "deleted_ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Stable ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for stable_id in ids:
                    Stables.objects.filter(id=stable_id).delete()
                    deleted_ids.append(stable_id)
            return JsonResponse({'message': 'Stables deleted successfully', 'deleted_ids': deleted_ids}, status=200)
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
