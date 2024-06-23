from django.http import JsonResponse
from horses_database.models import Accessories, AccessoryTypes, HorsesAccessories, Horses
import json
from django.db import transaction, IntegrityError, DatabaseError
from django.views.decorators.csrf import csrf_exempt


class HorsesAccessoriesView:
    @staticmethod
    @csrf_exempt
    def get_all_horses_accessories(request):
        """
        Retrieve all horses_accessories.
        Example JSON request: N/A
        Example JSON response:
        {
            "horses_accessories": [
                {
                    "id": 1,
                    "horse": {
                        "id": 1,
                        "breed": "Thoroughbred",
                        "height": "16.00",
                        "color": "Bay",
                        "eye_color": "Brown",
                        "age": 5,
                        "origin": "United States",
                        "hairstyle": "Mane and Tail"
                    },
                    "accessory": {
                        "id": 1,
                        "name": "English Saddle",
                        "type": {
                            "id": 1,
                            "type_name": "Saddles"
                        }
                    }
                }
            ]
        }
        """
        try:
            horses_accessories = HorsesAccessories.objects.select_related(
                'horse', 'accessory__type'
            ).all()

            response_data = []
            for ha in horses_accessories:
                horse_data = {
                    "id": ha.horse.id,
                    "breed": ha.horse.breed,
                    "height": str(ha.horse.height),
                    "color": ha.horse.color,
                    "eye_color": ha.horse.eye_color,
                    "age": ha.horse.age,
                    "origin": ha.horse.origin,
                    "hairstyle": ha.horse.hairstyle,
                }

                accessory_data = {
                    "id": ha.accessory.id,
                    "name": ha.accessory.name,
                    "type": {
                        "id": ha.accessory.type.id,
                        "type_name": ha.accessory.type.type_name,
                    }
                }

                response_data.append({
                    "id": ha.id,
                    "horse": horse_data,
                    "accessory": accessory_data,
                })

            return JsonResponse({"horses_accessories": response_data}, status=200)
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
    def get_horses_accessories_by_id(request):
        """
        Example JSON request:
        {
            "ids": [1]
        }
        Example JSON response:
        {
            "horses_accessories": [
                {
                    "id": 1,
                    "horse": {
                        "id": 1,
                        "breed": "Thoroughbred",
                        "height": "16.00",
                        "color": "Bay",
                        "eye_color": "Brown",
                        "age": 5,
                        "origin": "United States",
                        "hairstyle": "Mane and Tail"
                    },
                    "accessory": {
                        "id": 1,
                        "name": "English Saddle",
                        "type": {
                            "id": 1,
                            "type_name": "Saddles"
                        }
                    }
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Horses Accessory ID is required'}, status=400)
            horses_accessories = HorsesAccessories.objects.filter(id__in=ids).select_related(
                'horse', 'accessory__type'
            ).all()

            response_data = []
            for ha in horses_accessories:
                horse_data = {
                    "id": ha.horse.id,
                    "breed": ha.horse.breed,
                    "height": str(ha.horse.height),
                    "color": ha.horse.color,
                    "eye_color": ha.horse.eye_color,
                    "age": ha.horse.age,
                    "origin": ha.horse.origin,
                    "hairstyle": ha.horse.hairstyle,
                }

                accessory_data = {
                    "id": ha.accessory.id,
                    "name": ha.accessory.name,
                    "type": {
                        "id": ha.accessory.type.id,
                        "type_name": ha.accessory.type.type_name,
                    }
                }

                response_data.append({
                    "id": ha.id,
                    "horse": horse_data,
                    "accessory": accessory_data,
                })

            return JsonResponse({"horses_accessories": response_data}, status=200)

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
    def add_horses_accessory(request):
        """
        Add new accessories.
        Example JSON request:
        {
            "horses_accessories": [
                {
                    "horse": {
                        "id": 1
                    },
                    "accessory": {
                        "id": 1
                    }
                }
            ]
        }

        Example JSON response:
        {
            "message": "Horses Accessories added successfully",
            "ids": [1]
        }
        """
        try:
            data = json.loads(request.body)
            horses_accessories_data = data.get('horses_accessories', [])

            if not horses_accessories_data:
                return JsonResponse({'error': 'No horses accessories provided'}, status=400)

            added_ids = []

            with transaction.atomic():
                for horse_accessory_data in horses_accessories_data:
                    horse_id = horse_accessory_data.get('horse', {}).get('id')
                    accessory_id = horse_accessory_data.get('accessory', {}).get('id')

                    if not Horses.objects.filter(id=horse_id).exists():
                        return JsonResponse({'error': f'Horse with ID {horse_id} does not exist'}, status=400)

                    if not Accessories.objects.filter(id=accessory_id).exists():
                        return JsonResponse({'error': f'Accessory with ID {accessory_id} does not exist'}, status=400)

                    horses_accessory = HorsesAccessories.objects.create(horse_id=horse_id, accessory_id=accessory_id)
                    added_ids.append(horses_accessory.id)

            return JsonResponse({'message': 'Horses Accessories added successfully', 'ids': added_ids}, status=201)

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
    def update_horse_accessory(request):
        """
        Add new accessories.
        Example JSON request:
        {
            "horses_accessories": [
                {
                    "id": 1
                    "horse": {
                        "id": 1
                    },
                    "accessory": {
                        "id": 1
                    }
                }
            ]
        }

        Example JSON response:
        {
            "message": "Horses Accessories updated successfully",
            "ids": [1]
        }
        """
        try:
            data = json.loads(request.body)
            horses_accessories_data = data.get('horses_accessories', [])

            if not horses_accessories_data:
                return JsonResponse({'error': 'No horses accessories provided'}, status=400)

            added_ids = []

            with transaction.atomic():
                for horse_accessory_data in horses_accessories_data:
                    horse_accessory_id = horse_accessory_data.get('id')
                    horse_id = horse_accessory_data.get('horse', {}).get('id')
                    accessory_id = horse_accessory_data.get('accessory', {}).get('id')

                    if not Horses.objects.filter(id=horse_id).exists():
                        return JsonResponse({'error': f'Horse with ID {horse_id} does not exist'}, status=400)

                    if not Accessories.objects.filter(id=accessory_id).exists():
                        return JsonResponse({'error': f'Accessory with ID {accessory_id} does not exist'}, status=400)

                    if not HorsesAccessories.objects.filter(id=horse_accessory_id).exists():
                        return JsonResponse({'error': f'Horse Accessory with ID {horse_accessory_id} does not exist'}, status=400)

                    HorsesAccessories.objects.filter(id=horse_accessory_id).update(horse_id=horse_id,
                                                                                   accessory_id=accessory_id)
                    added_ids.append(horse_accessory_id)

            return JsonResponse({'message': 'Horses Accessories updated successfully', 'ids': added_ids},
                                status=201)

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
    def delete_horses_accessory(request):
        """
        Delete existing horses accessories.
        Example JSON request:
        {
            "horses_accessories_ids": [1, 2, 3]
        }

        Example JSON response:
            {
                "message": "Horses Accessories deleted successfully",
                "ids": [1, 3]
            }
        """
        try:
            data = json.loads(request.body)
            accessory_ids = data.get('accessory_ids', [])

            if not accessory_ids:
                return JsonResponse({'error': 'No horse accessory IDs provided'}, status=400)

            deleted_ids = []

            with transaction.atomic():
                for i_id in accessory_ids:
                    accessory = HorsesAccessories.objects.filter(id=i_id).first()
                    if accessory:
                        accessory.delete()
                        deleted_ids.append(i_id)
                    else:
                        return JsonResponse({'error': f'Horses Accessory with id {i_id} does not exist'}, status=404)

            return JsonResponse({'message': 'Horses Accessories deleted successfully', 'ids': deleted_ids}, status=200)

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

