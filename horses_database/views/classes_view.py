from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Classes, Employees, Groups, Stables
import json


class ClassesView:
    @staticmethod
    @csrf_exempt
    def get_all_classes(request):
        """
                Get all classes.
                Example JSON request: N/A
                Example JSON response:
                {
                    "classes": [
                        {
                            "id": 1,
                            "type": "Training",
                            "date": "2023-06-15",
                            "trainer": {
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
                                    "is_active": True,
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
                                "salary": "1000",
                                "date_employed": "1985-05-15"
                            },
                            "group": {
                                "id": 1,
                                "name": "Jumpers",
                                "max_group_members": 10
                            },
                            "stable": {
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
                            }
                        }
                    ]
                }
                """
        try:
            classes = Classes.objects.select_related(
                'trainer__member__address', 'trainer__member__licence',
                'trainer__position__licence', 'trainer__position__coaching_licence',
                'group', 'stable__address'
            ).all()

            classes_list = []
            for class_ in classes:
                class_data = {
                    "id": class_.id,
                    "type": class_.type,
                    "date": str(class_.date),
                    "trainer": {
                        "id": class_.trainer.id,
                        "member": {
                            "id": class_.trainer.member.id,
                            "name": class_.trainer.member.name,
                            "surname": class_.trainer.member.surname,
                            "username": class_.trainer.member.username,
                            "date_of_birth": str(class_.trainer.member.date_of_birth),
                            "address": {
                                "id": class_.trainer.member.address.id,
                                "country": class_.trainer.member.address.country,
                                "city": class_.trainer.member.address.city,
                                "street": class_.trainer.member.address.street,
                                "street_no": class_.trainer.member.address.street_no,
                                "postal_code": class_.trainer.member.address.postal_code
                            },
                            "phone_number": class_.trainer.member.phone_number,
                            "email": class_.trainer.member.email,
                            "is_active": class_.trainer.member.is_active,
                            "licence": {
                                "id": class_.trainer.member.licence.id,
                                "licence_level": class_.trainer.member.licence.licence_level
                            }
                        },
                        "position": {
                            "id": class_.trainer.position.id,
                            "name": class_.trainer.position.name,
                            "salary_min": str(class_.trainer.position.salary_min),
                            "salary_max": str(class_.trainer.position.salary_max),
                            "licence": {
                                "id": class_.trainer.position.licence.id,
                                "licence_level": class_.trainer.position.licence.licence_level
                            },
                            "coaching_licence": {
                                "id": class_.trainer.position.coaching_licence.id,
                                "licence_level": class_.trainer.position.coaching_licence.licence_level
                            },
                            "speciality": class_.trainer.position.speciality
                        },
                        "salary": str(class_.trainer.salary),
                        "date_employed": str(class_.trainer.date_employed)
                    },
                    "group": {
                        "id": class_.group.id,
                        "name": class_.group.name,
                        "max_group_members": class_.group.max_group_members
                    },
                    "stable": {
                        "id": class_.stable.id,
                        "name": class_.stable.name,
                        "address": {
                            "id": class_.stable.address.id,
                            "country": class_.stable.address.country,
                            "city": class_.stable.address.city,
                            "street": class_.stable.address.street,
                            "street_no": class_.stable.address.street_no,
                            "postal_code": class_.stable.address.postal_code
                        }
                    }
                }
                classes_list.append(class_data)

            return JsonResponse({'classes': classes_list}, status=200)

        except IntegrityError:
            return JsonResponse({'error': 'Integrity error'}, status=400)
        except DatabaseError:
            return JsonResponse({'error': 'Database error'}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def get_class_by_id(request):
        """
                Get classes with given ID
                Example JSON request:
                {
                    "ids": [1]
                }

                Example JSON response:
                {
                    "classes": [
                        {
                            "id": 1,
                            "type": "Training",
                            "date": "2023-06-15",
                            "trainer": {
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
                                    "is_active": True,
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
                                "salary": "1000",
                                "date_employed": "1985-05-15"
                            },
                            "group": {
                                "id": 1,
                                "name": "Jumpers",
                                "max_group_members": 10
                            },
                            "stable": {
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
                            }
                        }
                    ]
                }
                """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Class ID is required'}, status=400)
            classes = Classes.objects.filter(id__in=ids).select_related(
                'trainer__member__address', 'trainer__member__licence',
                'trainer__position__licence', 'trainer__position__coaching_licence',
                'group', 'stable__address'
            ).all()

            classes_list = []
            for class_ in classes:
                class_data = {
                    "id": class_.id,
                    "type": class_.type,
                    "date": str(class_.date),
                    "trainer": {
                        "id": class_.trainer.id,
                        "member": {
                            "id": class_.trainer.member.id,
                            "name": class_.trainer.member.name,
                            "surname": class_.trainer.member.surname,
                            "username": class_.trainer.member.username,
                            "date_of_birth": str(class_.trainer.member.date_of_birth),
                            "address": {
                                "id": class_.trainer.member.address.id,
                                "country": class_.trainer.member.address.country,
                                "city": class_.trainer.member.address.city,
                                "street": class_.trainer.member.address.street,
                                "street_no": class_.trainer.member.address.street_no,
                                "postal_code": class_.trainer.member.address.postal_code
                            },
                            "phone_number": class_.trainer.member.phone_number,
                            "email": class_.trainer.member.email,
                            "is_active": class_.trainer.member.is_active,
                            "licence": {
                                "id": class_.trainer.member.licence.id,
                                "licence_level": class_.trainer.member.licence.licence_level
                            }
                        },
                        "position": {
                            "id": class_.trainer.position.id,
                            "name": class_.trainer.position.name,
                            "salary_min": str(class_.trainer.position.salary_min),
                            "salary_max": str(class_.trainer.position.salary_max),
                            "licence": {
                                "id": class_.trainer.position.licence.id,
                                "licence_level": class_.trainer.position.licence.licence_level
                            },
                            "coaching_licence": {
                                "id": class_.trainer.position.coaching_licence.id,
                                "licence_level": class_.trainer.position.coaching_licence.licence_level
                            },
                            "speciality": class_.trainer.position.speciality
                        },
                        "salary": str(class_.trainer.salary),
                        "date_employed": str(class_.trainer.date_employed)
                    },
                    "group": {
                        "id": class_.group.id,
                        "name": class_.group.name,
                        "max_group_members": class_.group.max_group_members
                    },
                    "stable": {
                        "id": class_.stable.id,
                        "name": class_.stable.name,
                        "address": {
                            "id": class_.stable.address.id,
                            "country": class_.stable.address.country,
                            "city": class_.stable.address.city,
                            "street": class_.stable.address.street,
                            "street_no": class_.stable.address.street_no,
                            "postal_code": class_.stable.address.postal_code
                        }
                    }
                }
                classes_list.append(class_data)

            return JsonResponse({'classes': classes_list}, status=200)

        except IntegrityError:
            return JsonResponse({'error': 'Integrity error'}, status=400)
        except DatabaseError:
            return JsonResponse({'error': 'Database error'}, status=500)
        except Exception as e:
            return JsonResponse({'error': str(e)}, status=500)

    @staticmethod
    @csrf_exempt
    def add_class(request):
        """
        Example JSON request:
        {
            "classes": [
                {
                    "type": "Training",
                    "date": "2023-06-15",
                    "trainer": {
                        "id": 1
                    "group": {
                        "id": 1
                    },
                    "stable": {
                        "id": 1
                        }
                    }
                },
                {
                    "type": "Practise",
                    "date": "2023-06-15",
                    "trainer": {
                        "id": 2
                    "group": {
                        "id": 2
                    },
                    "stable": {
                        "id": 1
                        }
                    }
                }
            ]
        }

        Example JSON response:
        {
            'message': 'Classes added successfully',
            'ids': [3, 4]
        }
        """
        try:
            data = json.loads(request.body)
            classes = data.get('members', [])
            new_classes_ids = []
            with transaction.atomic():
                for class_data in classes:
                    type = class_data.get('type')
                    date = class_data.get('date')
                    trainer_id = class_data.get('trainer', {}).get('id')
                    group_id = class_data.get('group', {}).get('id')
                    stable_id = class_data.get('stable', {}).get('id')

                    if not type or not date:
                        return JsonResponse({'error': 'Type and date are required'}, status=400)

                    if not Employees.objects.filter(id=trainer_id).exists():
                        return JsonResponse({'error': f'Employee with ID {trainer_id} does not exist'}, status=400)

                    if not Groups.objects.filter(id=group_id).exists():
                        return JsonResponse({'error': f'Group with ID {group_id} does not exist'}, status=400)

                    if not Stables.objects.filter(id=stable_id).exists():
                        return JsonResponse({'error': f'Stable with ID {stable_id} does not exist'}, status=400)

                    new_class = Classes.objects.create(type=type, date=date, trainer_id=trainer_id, group_id=group_id, stable_id=stable_id)
                    new_classes_ids.append(new_class.id)

            return JsonResponse({'message': 'Classes added successfully', 'ids': new_classes_ids}, status=200)
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
    def update_class(request):
        """
        Example JSON request:
        {
            "classes": [
                {
                    "id": 3,
                    "type": "Training",
                    "date": "2023-06-15",
                    "trainer": {
                        "id": 1
                    "group": {
                        "id": 1
                    },
                    "stable": {
                        "id": 1
                        }
                    }
                },
                {
                    "id": 4,
                    "type": "Practise",
                    "date": "2023-06-15",
                    "trainer": {
                        "id": 2
                    "group": {
                        "id": 3
                    },
                    "stable": {
                        "id": 1
                        }
                    }
                }
            ]
        }

        Example JSON response:
        {
            'message': 'Classes updated successfully',
            'ids': [3, 4]
        }
        """
        try:
            data = json.loads(request.body)
            classes = data.get('members', [])
            new_classes_ids = []
            with transaction.atomic():
                for class_data in classes:
                    class_id = class_data.get('id')
                    type = class_data.get('type')
                    date = class_data.get('date')
                    trainer_id = class_data.get('trainer', {}).get('id')
                    group_id = class_data.get('group', {}).get('id')
                    stable_id = class_data.get('stable', {}).get('id')

                    if not type or not date:
                        return JsonResponse({'error': 'Type and date are required'}, status=400)

                    if not Employees.objects.filter(id=trainer_id).exists():
                        return JsonResponse({'error': f'Employee with ID {trainer_id} does not exist'}, status=400)

                    if not Groups.objects.filter(id=group_id).exists():
                        return JsonResponse({'error': f'Group with ID {group_id} does not exist'}, status=400)

                    if not Stables.objects.filter(id=stable_id).exists():
                        return JsonResponse({'error': f'Stable with ID {stable_id} does not exist'}, status=400)

                    new_class = Classes.objects.filter(id=class_id).update(type=type, date=date, trainer_id=trainer_id, group_id=group_id, stable_id=stable_id)
                    new_classes_ids.append(new_class.id)

            return JsonResponse({'message': 'Classes added successfully', 'ids': new_classes_ids}, status=200)
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
        pass

    @staticmethod
    @csrf_exempt
    def delete_class(request):
        """
        Example JSON request:
        {
            'ids': [3, 4]
        }

        Example JSON response:
        {
            'message': 'Classes deleted successfully',
            'ids': [3, 4]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Member ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for class_id in ids:
                    Classes.objects.filter(id=class_id).delete()
                    deleted_ids.append(class_id)

            return JsonResponse({'message': 'Classes deleted successfully', 'deleted_ids': deleted_ids}, status=200)
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
