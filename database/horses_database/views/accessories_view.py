from django.http import JsonResponse
from horses_database.models import Accessories, AccessoryTypes
import json
from django.db import transaction, IntegrityError, DatabaseError
from django.views.decorators.csrf import csrf_exempt


class AccessoriesView:
    @staticmethod
    @csrf_exempt
    def get_all_accessories(request):
        """
        Retrieve all accessories.
        Example JSON response:
        {
          "accessories": [
            {
              "id": 1,
              "name": "English Saddle",
              "type": {
                "id": 1,
                "type_name": "Saddles"
              }
            },
            {
              "id": 2,
              "name": "Western Bridle",
              "type": {
                "id": 2,
                "type_name": "Bridles"
              }
            }
          ]
        }
        """
        try:
            accessories = Accessories.objects.select_related('type').values(
                'id', 'name', 'type__id', 'type__type_name'
            )

            data = [
                {
                    "id": accessory['id'],
                    "name": accessory['name'],
                    "type": {
                        "id": accessory['type__id'],
                        "type_name": accessory['type__type_name']
                    }
                }
                for accessory in accessories
            ]

            return JsonResponse({'accessories': data}, status=200)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_accessory_by_id(request):
        """
        Retrieve accessory details by ID.
        Example JSON payload:
        {
            "accessory_ids": [2, 4]
        }

        Example JSON response:
        {
            "accessories": [
                {
                    "id": 2,
                    "name": "Saddle",
                    "type": {
                        "id": 1,
                        "type_name": "Type A"
                    }
                },
                {
                    "id": 4,
                    "name": "Bridle",
                    "type": {
                        "id": 2,
                        "type_name": "Type B"
                    }
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            accessory_ids = data.get('accessory_ids', [])

            if not accessory_ids:
                return JsonResponse({'error': 'No accessory IDs provided'}, status=400)

            accessories = []
            for id in accessory_ids:
                accessory = Accessories.objects.filter(id=id).values(
                    'id', 'name', 'type_id', 'type__type_name'
                ).first()
                if accessory:
                    accessories.append({
                        'id': accessory['id'],
                        'name': accessory['name'],
                        'type': {
                            'id': accessory['type_id'],
                            'type_name': accessory['type__type_name']
                        }
                    })
                else:
                    return JsonResponse({'error': f'Accessory with id {id} does not exist'}, status=404)

            return JsonResponse({'accessories': accessories}, status=200)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_new_accessories(request):
        """
        Add new accessories.
        Example JSON payload:
        {
            "accessories": [
                {"name": "Saddle", "type_id": 1},
                {"name": "Bridle", "type_id": 2}
            ]
        }

        Example JSON response:
        {
            "message": "Accessories added successfully",
            "added_ids": [1, 2, 3]
        }
        """
        try:
            data = json.loads(request.body)
            accessories_data = data.get('accessories', [])

            if not accessories_data:
                return JsonResponse({'error': 'No accessories provided'}, status=400)

            added_ids = []

            with transaction.atomic():
                for accessory_data in accessories_data:
                    name = accessory_data.get('name')
                    type_id = accessory_data.get('type_id')

                    if not name or not type_id:
                        return JsonResponse({'error': 'Name and type_id are required for each accessory'}, status=400)

                    accessory_type = AccessoryTypes.objects.filter(id=type_id).first()
                    if not accessory_type:
                        return JsonResponse({'error': f'Accessory type with id {type_id} does not exist'}, status=404)

                    accessory = Accessories.objects.create(name=name, type=accessory_type)
                    added_ids.append(accessory.id)

            return JsonResponse({'message': 'Accessories added successfully', 'added_ids': added_ids}, status=201)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def update_accessories(request):
        """
        Update existing accessories.
        Example JSON payload:
        {
            "accessories": [
                {"id": 1, "name": "Updated Saddle", "type_id": 3},
                {"id": 2, "name": "Updated Bridle", "type_id": 2}
            ]
        }

        Example JSON response:
        {
            "message": "Accessories updated successfully",
            "updated_ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            accessories_data = data.get('accessories', [])

            if not accessories_data:
                return JsonResponse({'error': 'No accessories provided'}, status=400)

            with transaction.atomic():
                for accessory_data in accessories_data:
                    id = accessory_data.get('id')
                    name = accessory_data.get('name')
                    type_id = accessory_data.get('type_id')

                    if not id or not name or not type_id:
                        return JsonResponse({'error': 'ID, name, and type_id are required for each accessory'},
                                            status=400)

                    accessory = Accessories.objects.filter(id=id).first()
                    if accessory:
                        accessory.name = name
                        accessory.type_id = type_id
                        accessory.save()
                    else:
                        return JsonResponse({'error': f'Accessory with id {id} does not exist'}, status=404)

            return JsonResponse({'message': 'Accessories updated successfully'}, status=200)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @csrf_exempt
    @staticmethod
    def delete_accessories(request):
        """
        Delete existing accessories.
        Example JSON payload:
        {
            "accessory_ids": [1, 2, 3]
        }

        Example JSON response:
            {
                "message": "Accessories deleted successfully",
                "deleted_ids": [1, 3]
            }
        """
        try:
            data = json.loads(request.body)
            accessory_ids = data.get('accessory_ids', [])

            if not accessory_ids:
                return JsonResponse({'error': 'No accessory IDs provided'}, status=400)

            deleted_ids = []

            with transaction.atomic():
                for i_id in accessory_ids:
                    accessory = Accessories.objects.filter(id=i_id).first()
                    if accessory:
                        accessory.delete()
                        deleted_ids.append(i_id)
                    else:
                        return JsonResponse({'error': f'Accessory with id {i_id} does not exist'}, status=404)

            return JsonResponse({'message': 'Accessories deleted successfully', 'deleted_ids': deleted_ids}, status=200)

        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON'}, status=400)
        except IntegrityError as e:
            return JsonResponse({'error': 'Integrity error: ' + str(e)}, status=400)
        except DatabaseError as e:
            return JsonResponse({'error': 'Database error: ' + str(e)}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)
