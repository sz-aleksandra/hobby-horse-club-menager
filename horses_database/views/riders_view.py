from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Riders, Members, Addresses, Licences, Classes
import json


class RidersView:
    @staticmethod
    @csrf_exempt
    def get_all_riders(request):
        """
        Get all riders.
        Example request JSON: N/A
        Example response JSON:
        {
            "riders": [
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
                    "parent_consent": true,
                    "group": {
                        "id": 1,
                        "name": "Jumpers",
                        "max_group_members": 10
                    },
                    "horse": {
                        "id": 1,
                        "breed": "Thoroughbred",
                        "height": "170 cm",
                        "color": "Bay",
                        "eye_color": "Brown",
                        "age": 7,
                        "origin": "USA",
                        "hairstyle": "Short"
                    }
                }
            ]
        }
        """
        try:
            riders = Riders.objects.select_related('member', 'group', 'horse').values(
                'id',
                'member_id', 'member__name', 'member__surname', 'member__username', 'member__date_of_birth',
                'member__address_id', 'member__address__country', 'member__address__city', 'member__address__street',
                'member__address__street_no', 'member__address__postal_code', 'member__phone_number', 'member__email',
                'member__is_active', 'member__licence_id', 'member__licence__licence_level',
                'parent_consent',
                'group_id', 'group__name', 'group__max_group_members',
                'horse_id', 'horse__breed', 'horse__height', 'horse__color', 'horse__eye_color', 'horse__age',
                'horse__origin', 'horse__hairstyle'
            )

            data = []
            for rider in riders:
                rider_data = {
                    "id": rider['id'],
                    "member": {
                        "id": rider['member_id'],
                        "name": rider['member__name'],
                        "surname": rider['member__surname'],
                        "username": rider['member__username'],
                        "date_of_birth": rider['member__date_of_birth'],
                        "address": {
                            "id": rider['member__address_id'],
                            "country": rider['member__address__country'],
                            "city": rider['member__address__city'],
                            "street": rider['member__address__street'],
                            "street_no": rider['member__address__street_no'],
                            "postal_code": rider['member__address__postal_code']
                        },
                        "phone_number": rider['member__phone_number'],
                        "email": rider['member__email'],
                        "is_active": rider['member__is_active'],
                        "licence": {
                            "id": rider['member__licence_id'],
                            "licence_level": rider['member__licence__licence_level'],
                        }
                    },
                    "parent_consent": rider['parent_consent'],  # Poprawiona nazwa pola
                    "group": {
                        "id": rider['group_id'],
                        "name": rider['group__name'],
                        "max_group_members": rider['group__max_group_members']
                    },
                    "horse": {
                        "id": rider['horse_id'],
                        "breed": rider['horse__breed'],
                        "height": rider['horse__height'],
                        "color": rider['horse__color'],
                        "eye_color": rider['horse__eye_color'],
                        "age": rider['horse__age'],
                        "origin": rider['horse__origin'],
                        "hairstyle": rider['horse__hairstyle']
                    }
                }
                data.append(rider_data)

            return JsonResponse({'riders': data}, status=200)
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
    def get_rider_by_id(request):
        """
        Get riders by IDs.
        Example JSON request:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "riders": [
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
                    "parent_consent_id": true,
                    "group": {
                        "id": 1,
                        "name": "Jumpers",
                        "max_group_members": 10
                    },
                    "horse": {
                        "id": 1,
                        "breed": "Thoroughbred",
                        "height": "170 cm",
                        "color": "Bay",
                        "eye_color": "Brown",
                        "age": 7,
                        "origin": "USA",
                        "hairstyle": "Short"
                    }
                },
                {
                    "id": 2,
                    "member": {
                        "id": 2,
                        "name": "Jane",
                        "surname": "Smith",
                        "username": "janesmith",
                        "date_of_birth": "1988-05-15",
                        "address": {
                            "id": 2,
                            "country": "Canada",
                            "city": "Toronto",
                            "street": "King Street",
                            "street_no": "456",
                            "postal_code": "M5V 1J6"
                        },
                        "phone_number": "+9876543210",
                        "email": "jane.smith@example.com",
                        "is_active": true,
                        "licence": {
                            "id": 2,
                            "licence_level": "B"
                        }
                    },
                    "parent_consent_id": false,
                    "group": {
                        "id": 2,
                        "name": "Dressage Team",
                        "max_group_members": 5
                    },
                    "horse": {
                        "id": 2,
                        "breed": "Warmblood",
                        "height": "165 cm",
                        "color": "Chestnut",
                        "eye_color": "Blue",
                        "age": 9,
                        "origin": "Germany",
                        "hairstyle": "Long"
                    }
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Rider ID is required'}, status=400)

            riders = Riders.objects.filter(id__in=ids).select_related('member', 'group', 'horse').values(
                'id',
                'member_id', 'member__name', 'member__surname', 'member__username', 'member__date_of_birth',
                'member__address_id', 'member__address__country', 'member__address__city', 'member__address__street',
                'member__address__street_no', 'member__address__postal_code', 'member__phone_number', 'member__email',
                'member__is_active', 'member__licence__id', 'member__licence__licence_level', 'parent_consent',
                'group_id', 'group__name', 'group__max_group_members',
                'horse_id', 'horse__breed', 'horse__height', 'horse__color', 'horse__eye_color', 'horse__age',
                'horse__origin', 'horse__hairstyle'
            )

            data = [
                {
                    "id": rider['id'],
                    "member": {
                        "id": rider['member_id'],
                        "name": rider['member__name'],
                        "surname": rider['member__surname'],
                        "username": rider['member__username'],
                        "date_of_birth": rider['member__date_of_birth'],
                        "address": {
                            "id": rider['member__address_id'],
                            "country": rider['member__address__country'],
                            "city": rider['member__address__city'],
                            "street": rider['member__address__street'],
                            "street_no": rider['member__address__street_no'],
                            "postal_code": rider['member__address__postal_code']
                        },
                        "phone_number": rider['member__phone_number'],
                        "email": rider['member__email'],
                        "is_active": rider['member__is_active'],
                        "licence": {
                            "id": rider['member__licence__id'],
                            "licence_level": rider['member__licence__licence_level'],
                        }
                    },
                    "parent_consent_id": rider['parent_consent'],
                    "group": {
                        "id": rider['group_id'],
                        "name": rider['group__name'],
                        "max_group_members": rider['group__max_group_members']
                    },
                    "horse": {
                        "id": rider['horse_id'],
                        "breed": rider['horse__breed'],
                        "height": rider['horse__height'],
                        "color": rider['horse__color'],
                        "eye_color": rider['horse__eye_color'],
                        "age": rider['horse__age'],
                        "origin": rider['horse__origin'],
                        "hairstyle": rider['horse__hairstyle']
                    }
                }
                for rider in riders
            ]

            return JsonResponse({'riders': data}, status=200)
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
    def add_rider(request):
        """
        Add new riders.
        Example JSON request:
        {
            "riders": [
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
                            "id": 1
                        }
                    },
                    "parent_consent": true,
                    "group": {
                        "id": 1
                    },
                    "horse": {
                        "id": 1
                    }
                }
            ]
        }
        Example response JSON:
        {
            "message": "Riders added successfully",
            "ids": [4]
        }
        """
        try:
            data = json.loads(request.body)
            riders = data.get('riders', [])

            if not riders:
                return JsonResponse({'error': 'No riders provided'}, status=400)

            new_rider_ids = []
            with transaction.atomic():
                for rider_data in riders:
                    member_data = rider_data.get('member', {})
                    group_id = rider_data.get('group', {}).get('id')
                    horse_id = rider_data.get('horse', {}).get('id')
                    parent_consent = rider_data.get('parent_consent', False)

                    # Create Address object
                    address_data = member_data.get('address', {})
                    address = Addresses.objects.create(**address_data)

                    # Create Member object
                    licence_id = member_data.get('licence', {}).get('id')
                    new_member = Members.objects.create(
                        name=member_data.get('name'),
                        surname=member_data.get('surname'),
                        username=member_data.get('username'),
                        password=member_data.get('password'),
                        date_of_birth=member_data.get('date_of_birth'),
                        address_id=address.id,
                        phone_number=member_data.get('phone_number'),
                        email=member_data.get('email'),
                        is_active=member_data.get('is_active'),
                        licence_id=licence_id,
                    )

                    # Create Rider object
                    new_rider = Riders.objects.create(
                        member_id=new_member.id,
                        group_id=group_id,
                        horse_id=horse_id,
                        parent_consent=parent_consent
                    )
                    new_rider_ids.append(new_rider.id)

            return JsonResponse({'message': 'Riders added successfully', 'ids': new_rider_ids}, status=200)

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
    def update_rider(request):
        """
        Update riders by IDs.
        Example JSON request:
        {
            "riders": [
                {
                    "id": 1,
                    "member": {
                        "id": 1,
                        "name": "Updated John",
                        "surname": "Doe",
                        "username": "johndoe",
                        "password": "qwerty',
                        "date_of_birth": "1990-01-01",
                        "address": {
                            "id": 1,
                            "country": "United States",
                            "city": "New York",
                            "street": "Broadway",
                            "street_no": "123",
                            "postal_code": "10001"
                        },
                        "phone_number": "+1234567890",
                        "email": "john.doe@example.com",
                        "is_active": true,
                        "licence": {
                            "id": 1
                        }
                    },
                    "parent_consent": true,
                    "group_id": 1,
                    "horse_id": 1
                },
                {
                    "id": 2,
                    "member": {
                        "id": 2,
                        "name": "Updated Jane",
                        "surname": "Smith",
                        "username": "janesmith",
                        "date_of_birth": "1995-05-05",
                        "address": {
                            "id": 2,
                            "country": "United States",
                            "city": "Los Angeles",
                            "street": "Sunset Blvd",
                            "street_no": "456",
                            "postal_code": "90001"
                        },
                        "phone_number": "+1234567890",
                        "email": "john.doe@example.com",
                        "is_active": true,
                        "licence": {
                            "id": 1
                        }
                    },
                    "parent_consent": true,
                    "group_id": 2,
                    "horse_id": 2
                }
            ]
        }
        Example response JSON:
        {
            "message": "Riders updated successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            riders = data.get('riders', [])

            if not riders:
                return JsonResponse({'error': 'No riders provided'}, status=400)

            updated_ids = []
            with transaction.atomic():
                for rider_data in riders:
                    rider_id = rider_data.get('id')
                    member_data = rider_data.get('member', {})
                    member_id = member_data.get('id')
                    parent_consent = rider_data.get('parent_consent')
                    group_id = rider_data.get('group_id')
                    horse_id = rider_data.get('horse_id')

                    address_data = member_data.get('address')
                    licence_id = member_data.get('licence', {}).get('id')

                    if not rider_id or not member_id or not group_id or not horse_id:
                        return JsonResponse(
                            {'error': 'ID, member_id, group_id, horse_id are required fields'},
                            status=400
                        )
                    # Check if Member and Licence IDs exist
                    if not Members.objects.filter(id=member_id).exists():
                        return JsonResponse({'error': f'Member with ID {member_id} does not exist'}, status=400)
                    if not Licences.objects.filter(id=licence_id).exists():
                        return JsonResponse({'error': f'Licence with ID {licence_id} does not exist'}, status=400)

                    # Update Address if provided
                    if address_data.get('id'):
                        Addresses.objects.filter(id=address_data.get('id')).update(
                            country=address_data.get('country'),
                            city=address_data.get('city'),
                            street=address_data.get('street'),
                            street_no=address_data.get('street_no'),
                            postal_code=address_data.get('postal_code')
                        )

                    # Update Member
                    Members.objects.filter(id=member_id).update(
                        name=member_data.get('name'),
                        surname=member_data.get('surname'),
                        username=member_data.get('username'),
                        password=member_data.get('password'),
                        date_of_birth=member_data.get('date_of_birth'),
                        phone_number=member_data.get('phone_number'),
                        email=member_data.get('email'),
                        is_active=member_data.get('is_active'),
                        licence_id=licence_id,
                        address_id=address_data.get('id')
                    )

                    # Update rider
                    Riders.objects.filter(id=rider_id).update(
                        member_id=member_id,
                        parent_consent=parent_consent,
                        group_id=group_id,
                        horse_id=horse_id
                    )
                    updated_ids.append(rider_id)
            return JsonResponse({'message': 'Riders updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_rider(request):
        """
        Delete riders by IDs.
        Example JSON request:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "message": "Riders deleted successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Rider ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for rider_id in ids:
                    Riders.objects.filter(id=rider_id).delete()
                    deleted_ids.append(rider_id)

            return JsonResponse({'message': 'Riders deleted successfully', 'ids': deleted_ids}, status=200)
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
    def login_rider(request):
        """
        Login as rider
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
                rider = Riders.objects.get(member__username=login, member__is_active=True)
            except Riders.DoesNotExist:
                return JsonResponse({'error': 'Invalid login'}, status=401)

            if not password == rider.member.password:
                return JsonResponse({'error': 'Invalid password'}, status=401)

            return JsonResponse({'id': rider.id}, status=200)

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
    def get_classes_for_rider(request):
        """
        Get classes for rider
        Example JSON request:
        {
            "id": 1
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
            rider_id = data.get('id')

            if not rider_id :
                return JsonResponse({'error': 'No id provided'}, status=400)
            try:
                rider = Riders.objects.get(id=rider_id)
            except Riders.DoesNotExist:
                return JsonResponse({'error': 'Invalid rider id'}, status=401)

            classes = Classes.objects.filter(group_id=rider.group_id).select_related(
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
            rider_id = data.get('id')

            if not rider_id:
                return JsonResponse({'error': 'No id provided'}, status=400)
            try:
                rider = Riders.objects.get(id=rider_id)
            except Riders.DoesNotExist:
                return JsonResponse({'error': 'Invalid rider id'}, status=401)

            rider.member.is_active = False
            rider.member.save()
            return JsonResponse({'message': 'Successfully deactivated account', 'id': rider.id}, status=200)

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
