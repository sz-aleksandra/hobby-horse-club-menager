from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Horses
import json


class HorsesView:
    @staticmethod
    @csrf_exempt
    def get_all_horses(request):
        """
        Get all horses.
        Example request JSON: N/A
        Example response JSON:
        {
            "horses": [
                {
                    "id": 1,
                    "breed": "Thoroughbred",
                    "height": "16.00",
                    "color": "Bay",
                    "eye_color": "Brown",
                    "age": 5,
                    "origin": "United States",
                    "hairstyle": "Mane and Tail"
                },
                {
                    "id": 2,
                    "breed": "Quarter Horse",
                    "height": "15.10",
                    "color": "Sorrel",
                    "eye_color": "Blue",
                    "age": 8,
                    "origin": "United States",
                    "hairstyle": "Short and Smooth"
                }
            ]
        }
        """
        try:
            horses = list(Horses.objects.all().values())
            return JsonResponse({'horses': horses}, status=200)

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
    def get_horses_by_id(request):
        """
        Get horses by IDs.
        Example JSON request:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "horses": [
                {
                    "id": 1,
                    "breed": "Thoroughbred",
                    "height": "16.00",
                    "color": "Bay",
                    "eye_color": "Brown",
                    "age": 5,
                    "origin": "United States",
                    "hairstyle": "Mane and Tail"
                },
                {
                    "id": 2,
                    "breed": "Quarter Horse",
                    "height": "15.10",
                    "color": "Sorrel",
                    "eye_color": "Blue",
                    "age": 8,
                    "origin": "United States",
                    "hairstyle": "Short and Smooth"
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Horse ID is required'}, status=400)

            horses = list(Horses.objects.filter(id__in=ids).values())
            return JsonResponse({'horses': horses}, status=200)

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
    def add_horse(request):
        """
        Add new horses.
        Example JSON request:
        {
            "horses": [
                {
                    "breed": "Thoroughbred",
                    "height": "16.00",
                    "color": "Bay",
                    "eye_color": "Brown",
                    "age": 5,
                    "origin": "United States",
                    "hairstyle": "Mane and Tail"
                },
                {
                    "breed": "Quarter Horse",
                    "height": "15.10",
                    "color": "Sorrel",
                    "eye_color": "Blue",
                    "age": 8,
                    "origin": "United States",
                    "hairstyle": "Short and Smooth"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Horses added successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            horses = data.get('horses', [])

            if not horses:
                return JsonResponse({'error': 'No horses provided'}, status=400)

            horse_ids = []
            with transaction.atomic():
                for horse in horses:
                    breed = horse.get('breed')
                    height = horse.get('height')
                    color = horse.get('color')
                    eye_color = horse.get('eye_color')
                    age = horse.get('age')
                    origin = horse.get('origin')
                    hairstyle = horse.get('hairstyle')

                    if not breed or not height or not color or not eye_color or not age or not origin or not hairstyle:
                        return JsonResponse({
                                                'error': 'All fields (breed, height, color, eye_color, age, origin, hairstyle) are required'},
                                            status=400)

                    new_horse = Horses.objects.create(
                        breed=breed,
                        height=height,
                        color=color,
                        eye_color=eye_color,
                        age=age,
                        origin=origin,
                        hairstyle=hairstyle
                    )
                    horse_ids.append(new_horse.id)
            return JsonResponse({'message': 'Horses added successfully', 'ids': horse_ids}, status=201)

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
    def update_horse(request):
        """
        Update horses by IDs.
        Example JSON request:
        {
            "horses": [
                {
                    "id": 4,
                    "breed": "Updated Thoroughbred",
                    "height": "16.05",
                    "color": "Bay",
                    "eye_color": "Brown",
                    "age": 6,
                    "origin": "United States",
                    "hairstyle": "Updated Mane and Tail"
                },
                {
                    "id": 5,
                    "breed": "Updated Quarter Horse",
                    "height": "15.15",
                    "color": "Sorrel",
                    "eye_color": "Blue",
                    "age": 9,
                    "origin": "United States",
                    "hairstyle": "Updated Short and Smooth"
                }
            ]
        }
        Example response JSON:
        {
            "message": "Horses updated successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            horses = data.get('horses', [])

            if not horses:
                return JsonResponse({'error': 'No horses provided'}, status=400)

            updated_ids = []
            with transaction.atomic():
                for horse in horses:
                    horse_id = horse.get('id')
                    breed = horse.get('breed')
                    height = horse.get('height')
                    color = horse.get('color')
                    eye_color = horse.get('eye_color')
                    age = horse.get('age')
                    origin = horse.get('origin')
                    hairstyle = horse.get('hairstyle')

                    if not horse_id or not breed or not height or not color or not eye_color or not age or not origin or not hairstyle:
                        return JsonResponse({
                                                'error': 'ID and all fields (breed, height, color, eye_color, age, origin, hairstyle) are required'},
                                            status=400)

                    Horses.objects.filter(id=horse_id).update(
                        breed=breed,
                        height=height,
                        color=color,
                        eye_color=eye_color,
                        age=age,
                        origin=origin,
                        hairstyle=hairstyle
                    )
                    updated_ids.append(horse_id)
            return JsonResponse({'message': 'Horses updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_horse(request):
        """
        Delete horses by IDs.
        Example JSON request:
        {
            "ids": [4, 5]
        }
        Example response JSON:
        {
            "message": "Horses deleted successfully",
            "ids": [4, 5]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Horse ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for horse_id in ids:
                    Horses.objects.filter(id=horse_id).delete()
                    deleted_ids.append(horse_id)
            return JsonResponse({'message': 'Horses deleted successfully', 'ids': deleted_ids}, status=200)
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
