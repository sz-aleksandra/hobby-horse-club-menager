from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction, IntegrityError, DatabaseError
from horses_database.models import Tournaments, Addresses, Employees, Riders, TournamentParticipants
import json


class TournamentsView:
    @staticmethod
    @csrf_exempt
    def get_all_tournaments(request):
        """
        Get all tournaments.
        Example response JSON:
        {
            "tournaments": [
                {
                    "id": 1,
                    "name": "Championship",
                    "date": "1990-01-01",
                    "address": {
                        "id": 1,
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
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
                }
            ]
        }
        """
        try:
            tournaments = Tournaments.objects.select_related(
                'address', 'judge__member__address', 'judge__member__licence',
                'judge__position__licence', 'judge__position__coaching_licence'
            ).values(
                'id', 'name', 'date', 'address_id', 'address__country', 'address__city', 'address__street',
                'address__street_no', 'address__postal_code', 'judge_id', 'judge__member_id',
                'judge__member__name', 'judge__member__surname', 'judge__member__username',
                'judge__member__date_of_birth', 'judge__member__address_id', 'judge__member__address__country',
                'judge__member__address__city', 'judge__member__address__street',
                'judge__member__address__street_no', 'judge__member__address__postal_code',
                'judge__member__phone_number', 'judge__member__email', 'judge__member__is_active',
                'judge__member__licence_id', 'judge__member__licence__licence_level',
                'judge__position_id', 'judge__position__name', 'judge__position__salary_min',
                'judge__position__salary_max', 'judge__position__licence_id',
                'judge__position__licence__licence_level', 'judge__position__coaching_licence_id',
                'judge__position__coaching_licence__licence_level', 'judge__salary',
                'judge__date_employed'
            )

            data = []
            for tournament in tournaments:
                tournament_data = {
                    "id": tournament['id'],
                    "name": tournament['name'],
                    "date": tournament['date'],
                    "address": {
                        "id": tournament['address_id'],
                        "country": tournament['address__country'],
                        "city": tournament['address__city'],
                        "street": tournament['address__street'],
                        "street_no": tournament['address__street_no'],
                        "postal_code": tournament['address__postal_code']
                    },
                    "judge": {
                        "id": tournament['judge_id'],
                        "member": {
                            "id": tournament['judge__member_id'],
                            "name": tournament['judge__member__name'],
                            "surname": tournament['judge__member__surname'],
                            "username": tournament['judge__member__username'],
                            "date_of_birth": tournament['judge__member__date_of_birth'],
                            "address": {
                                "id": tournament['judge__member__address_id'],
                                "country": tournament['judge__member__address__country'],
                                "city": tournament['judge__member__address__city'],
                                "street": tournament['judge__member__address__street'],
                                "street_no": tournament['judge__member__address__street_no'],
                                "postal_code": tournament['judge__member__address__postal_code']
                            },
                            "phone_number": tournament['judge__member__phone_number'],
                            "email": tournament['judge__member__email'],
                            "is_active": tournament['judge__member__is_active'],
                            "licence": {
                                "id": tournament['judge__member__licence_id'],
                                "licence_level": tournament['judge__member__licence__licence_level']
                            }
                        },
                        "position": {
                            "id": tournament['judge__position_id'],
                            "name": tournament['judge__position__name'],
                            "salary_min": tournament['judge__position__salary_min'],
                            "salary_max": tournament['judge__position__salary_max'],
                            "licence": {
                                "id": tournament['judge__position__licence_id'],
                                "licence_level": tournament['judge__position__licence__licence_level']
                            },
                            "coaching_licence": {
                                "id": tournament['judge__position__coaching_licence_id'],
                                "licence_level": tournament['judge__position__coaching_licence__licence_level']
                            }
                        },
                        "salary": tournament['judge__salary'],
                        "date_employed": tournament['judge__date_employed']
                    }
                }
                data.append(tournament_data)
            return JsonResponse({'tournaments': data}, status=200)
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
    def get_tournament_by_id(request):
        """
        Example request JSON:
        {
            "ids": [1]
        }
        Example response JSON:
        {
            "tournaments": [
                {
                    "id": 1,
                    "name": "Championship",
                    "date": "1990-01-01",
                    "address": {
                        "id": 1,
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
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
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Tournament ID is required'}, status=400)
            tournaments = Tournaments.objects.filter(id__in=ids).select_related(
                'address', 'judge__member__address', 'judge__member__licence',
                'judge__position__licence', 'judge__position__coaching_licence'
            ).values(
                'id', 'name', 'date', 'address_id', 'address__country', 'address__city', 'address__street',
                'address__street_no', 'address__postal_code', 'judge_id', 'judge__member_id',
                'judge__member__name', 'judge__member__surname', 'judge__member__username',
                'judge__member__date_of_birth', 'judge__member__address_id', 'judge__member__address__country',
                'judge__member__address__city', 'judge__member__address__street',
                'judge__member__address__street_no', 'judge__member__address__postal_code',
                'judge__member__phone_number', 'judge__member__email', 'judge__member__is_active',
                'judge__member__licence_id', 'judge__member__licence__licence_level',
                'judge__position_id', 'judge__position__name', 'judge__position__salary_min',
                'judge__position__salary_max', 'judge__position__licence_id',
                'judge__position__licence__licence_level', 'judge__position__coaching_licence_id',
                'judge__position__coaching_licence__licence_level', 'judge__salary',
                'judge__date_employed'
            )
            data = []
            for tournament in tournaments:
                tournament_data = {
                    "id": tournament['id'],
                    "name": tournament['name'],
                    "date": tournament['date'],
                    "address": {
                        "id": tournament['address_id'],
                        "country": tournament['address__country'],
                        "city": tournament['address__city'],
                        "street": tournament['address__street'],
                        "street_no": tournament['address__street_no'],
                        "postal_code": tournament['address__postal_code']
                    },
                    "judge": {
                        "id": tournament['judge_id'],
                        "member": {
                            "id": tournament['judge__member_id'],
                            "name": tournament['judge__member__name'],
                            "surname": tournament['judge__member__surname'],
                            "username": tournament['judge__member__username'],
                            "date_of_birth": tournament['judge__member__date_of_birth'],
                            "address": {
                                "id": tournament['judge__member__address_id'],
                                "country": tournament['judge__member__address__country'],
                                "city": tournament['judge__member__address__city'],
                                "street": tournament['judge__member__address__street'],
                                "street_no": tournament['judge__member__address__street_no'],
                                "postal_code": tournament['judge__member__address__postal_code']
                            },
                            "phone_number": tournament['judge__member__phone_number'],
                            "email": tournament['judge__member__email'],
                            "is_active": tournament['judge__member__is_active'],
                            "licence": {
                                "id": tournament['judge__member__licence_id'],
                                "licence_level": tournament['judge__member__licence__licence_level']
                            }
                        },
                        "position": {
                            "id": tournament['judge__position_id'],
                            "name": tournament['judge__position__name'],
                            "salary_min": tournament['judge__position__salary_min'],
                            "salary_max": tournament['judge__position__salary_max'],
                            "licence": {
                                "id": tournament['judge__position__licence_id'],
                                "licence_level": tournament['judge__position__licence__licence_level']
                            },
                            "coaching_licence": {
                                "id": tournament['judge__position__coaching_licence_id'],
                                "licence_level": tournament['judge__position__coaching_licence__licence_level']
                            }
                        },
                        "salary": tournament['judge__salary'],
                        "date_employed": tournament['judge__date_employed']
                    }
                }
                data.append(tournament_data)
            return JsonResponse({'tournaments': data}, status=200)
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
    def add_tournament(request):
        """
        Example request JSON:
        {
            "tournaments": [
                {
                    "name": "Championship",
                    "date": "1990-01-01",
                    "address": {
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
                        "id": 1
                        },
                    }
                }
            ]
        }
        Example response JSON:
        {
            'message': 'Tournaments added successfully',
            'ids': [1]
        }
        """
        try:
            data = json.loads(request.body)
            tournaments = data.get('tournaments', [])
            new_tournament_ids = []
            with transaction.atomic():
                for tournament_data in tournaments:
                    address_data = tournament_data.pop('address')
                    judge_id = tournament_data.get('judge', {}).get('id')

                    # Check if Licence ID exists
                    if judge_id:
                        if not Employees.objects.filter(id=judge_id).exists():
                            return JsonResponse({'error': f'Employee with ID {judge_id} does not exist'}, status=400)

                    # Create Address object
                    if address_data:
                        address = Addresses.objects.create(**address_data)
                        tournament_data['address_id'] = address.id

                    # Create Tournament object
                    new_tournament = Tournaments.objects.create(name=tournament_data.get('name'),
                                                                date=tournament_data.get('date'),
                                                                address_id=address.id,
                                                                judge_id=judge_id)
                    new_tournament_ids.append(new_tournament.id)

            return JsonResponse({'message': 'Tournaments added successfully', 'ids': new_tournament_ids}, status=201)
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
    def update_tournament(request):
        """
        Example request JSON:
        {
            "tournaments": [
                {
                    "id": 1
                    "name": "Championship",
                    "date": "1990-01-01",
                    "address": {
                        "id": 1
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
                        "id": 1
                        },
                    }
                }
            ]
        }
        Example response JSON:
        {
            'message': 'Tournaments updated successfully',
            'ids': [1]
        }
        """
        try:
            data = json.loads(request.body)
            tournaments = data.get('tournaments', [])
            updated_ids = []
            with transaction.atomic():
                for tournament_data in tournaments:
                    tournament_id = tournament_data.get('id')
                    address_data = tournament_data.get('address')
                    address_id = address_data.get('id')
                    judge_id = tournament_data.get('judge', {}).get('id')

                    # Check if Licence ID exists
                    if not Employees.objects.filter(id=judge_id).exists():
                        return JsonResponse({'error': f'Employee with ID {judge_id} does not exist'}, status=400)

                    if not Addresses.objects.filter(id=address_id).exists():
                        return JsonResponse({'error': f'Address with ID {address_id} does not exist'}, status=400)

                    if not Tournaments.objects.filter(id=tournament_id).exists():
                        return JsonResponse({'error': f'Tournament with ID {tournament_id} does not exist'}, status=400)

                    # Update Address
                    address = Addresses.objects.filter(id=address_id).update(country=address_data.get('country'),
                                                                             city=address_data.get('city'),
                                                                             street=address_data.get('street'),
                                                                             street_no=address_data.get('street_no'),
                                                                             postal_code=address_data.get(
                                                                                 'postal_code'))

                    # Update Tournament
                    Tournaments.objects.filter(id=tournament_id).update(
                        name=tournament_data.get('name'),
                        date=tournament_data.get('date'),
                        address_id=address_id,
                        judge_id=judge_id)
                    updated_ids.append(tournament_id)

            return JsonResponse({'message': 'Tournaments updated successfully', 'ids': updated_ids}, status=200)
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
    def delete_tournament(request):
        """
        Delete tournament by IDs.
        Example JSON payload:
        {
            "ids": [1, 2]
        }
        Example response JSON:
        {
            "message": "Tournaments deleted successfully",
            "ids": [1, 2]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Tournament ID is required'}, status=400)

            deleted_ids = []
            with transaction.atomic():
                for tournament_id in ids:
                    Tournaments.objects.filter(id=tournament_id).delete()
                    deleted_ids.append(tournament_id)

            return JsonResponse({'message': 'Tournaments deleted successfully', 'ids': deleted_ids}, status=200)
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
    def get_tournaments_for_rider(request):
        """
        Example JSON request:
        {
            "id": 1
        }

        Example JSON response:
        {
            "tournaments": [
                {
                    "id": 1,
                    "name": "Championship",
                    "date": "1990-01-01",
                    "address": {
                        "id": 1,
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
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
                }
            ]
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

            tournament_ids = list(TournamentParticipants.objects.filter(
                contestant_id=rider.id).values_list('tournament_id', flat=True))
            tournaments = Tournaments.objects.filter(id__in=tournament_ids).select_related(
                'address', 'judge__member__address', 'judge__member__licence',
                'judge__position__licence', 'judge__position__coaching_licence'
            ).values(
                'id', 'name', 'date', 'address_id', 'address__country', 'address__city', 'address__street',
                'address__street_no', 'address__postal_code', 'judge_id', 'judge__member_id',
                'judge__member__name', 'judge__member__surname', 'judge__member__username',
                'judge__member__date_of_birth', 'judge__member__address_id', 'judge__member__address__country',
                'judge__member__address__city', 'judge__member__address__street',
                'judge__member__address__street_no', 'judge__member__address__postal_code',
                'judge__member__phone_number', 'judge__member__email', 'judge__member__is_active',
                'judge__member__licence_id', 'judge__member__licence__licence_level',
                'judge__position_id', 'judge__position__name', 'judge__position__salary_min',
                'judge__position__salary_max', 'judge__position__licence_id',
                'judge__position__licence__licence_level', 'judge__position__coaching_licence_id',
                'judge__position__coaching_licence__licence_level', 'judge__salary',
                'judge__date_employed'
            )

            data = []
            for tournament in tournaments:
                tournament_data = {
                    "id": tournament['id'],
                    "name": tournament['name'],
                    "date": tournament['date'],
                    "address": {
                        "id": tournament['address_id'],
                        "country": tournament['address__country'],
                        "city": tournament['address__city'],
                        "street": tournament['address__street'],
                        "street_no": tournament['address__street_no'],
                        "postal_code": tournament['address__postal_code']
                    },
                    "judge": {
                        "id": tournament['judge_id'],
                        "member": {
                            "id": tournament['judge__member_id'],
                            "name": tournament['judge__member__name'],
                            "surname": tournament['judge__member__surname'],
                            "username": tournament['judge__member__username'],
                            "date_of_birth": tournament['judge__member__date_of_birth'],
                            "address": {
                                "id": tournament['judge__member__address_id'],
                                "country": tournament['judge__member__address__country'],
                                "city": tournament['judge__member__address__city'],
                                "street": tournament['judge__member__address__street'],
                                "street_no": tournament['judge__member__address__street_no'],
                                "postal_code": tournament['judge__member__address__postal_code']
                            },
                            "phone_number": tournament['judge__member__phone_number'],
                            "email": tournament['judge__member__email'],
                            "is_active": tournament['judge__member__is_active'],
                            "licence": {
                                "id": tournament['judge__member__licence_id'],
                                "licence_level": tournament['judge__member__licence__licence_level']
                            }
                        },
                        "position": {
                            "id": tournament['judge__position_id'],
                            "name": tournament['judge__position__name'],
                            "salary_min": tournament['judge__position__salary_min'],
                            "salary_max": tournament['judge__position__salary_max'],
                            "licence": {
                                "id": tournament['judge__position__licence_id'],
                                "licence_level": tournament['judge__position__licence__licence_level']
                            },
                            "coaching_licence": {
                                "id": tournament['judge__position__coaching_licence_id'],
                                "licence_level": tournament['judge__position__coaching_licence__licence_level']
                            }
                        },
                        "salary": tournament['judge__salary'],
                        "date_employed": tournament['judge__date_employed']
                    }
                }
                data.append(tournament_data)
            return JsonResponse({'tournaments': data}, status=200)

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
    def get_tournaments_for_employee(request):
        """
        Example JSON request:
        {
            "id": 1
        }

        Example JSON response:
        {
            "tournaments": [
                {
                    "id": 1,
                    "name": "Championship",
                    "date": "1990-01-01",
                    "address": {
                        "id": 1,
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
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
                }
            ]
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
                return JsonResponse({'error': 'Invalid rider id'}, status=401)

            tournaments = Tournaments.objects.filter(judge_id=employee.id).select_related(
                'address', 'judge__member__address', 'judge__member__licence',
                'judge__position__licence', 'judge__position__coaching_licence'
            ).values(
                'id', 'name', 'date', 'address_id', 'address__country', 'address__city', 'address__street',
                'address__street_no', 'address__postal_code', 'judge_id', 'judge__member_id',
                'judge__member__name', 'judge__member__surname', 'judge__member__username',
                'judge__member__date_of_birth', 'judge__member__address_id', 'judge__member__address__country',
                'judge__member__address__city', 'judge__member__address__street',
                'judge__member__address__street_no', 'judge__member__address__postal_code',
                'judge__member__phone_number', 'judge__member__email', 'judge__member__is_active',
                'judge__member__licence_id', 'judge__member__licence__licence_level',
                'judge__position_id', 'judge__position__name', 'judge__position__salary_min',
                'judge__position__salary_max', 'judge__position__licence_id',
                'judge__position__licence__licence_level', 'judge__position__coaching_licence_id',
                'judge__position__coaching_licence__licence_level', 'judge__salary',
                'judge__date_employed'
            )

            data = []
            for tournament in tournaments:
                tournament_data = {
                    "id": tournament['id'],
                    "name": tournament['name'],
                    "date": tournament['date'],
                    "address": {
                        "id": tournament['address_id'],
                        "country": tournament['address__country'],
                        "city": tournament['address__city'],
                        "street": tournament['address__street'],
                        "street_no": tournament['address__street_no'],
                        "postal_code": tournament['address__postal_code']
                    },
                    "judge": {
                        "id": tournament['judge_id'],
                        "member": {
                            "id": tournament['judge__member_id'],
                            "name": tournament['judge__member__name'],
                            "surname": tournament['judge__member__surname'],
                            "username": tournament['judge__member__username'],
                            "date_of_birth": tournament['judge__member__date_of_birth'],
                            "address": {
                                "id": tournament['judge__member__address_id'],
                                "country": tournament['judge__member__address__country'],
                                "city": tournament['judge__member__address__city'],
                                "street": tournament['judge__member__address__street'],
                                "street_no": tournament['judge__member__address__street_no'],
                                "postal_code": tournament['judge__member__address__postal_code']
                            },
                            "phone_number": tournament['judge__member__phone_number'],
                            "email": tournament['judge__member__email'],
                            "is_active": tournament['judge__member__is_active'],
                            "licence": {
                                "id": tournament['judge__member__licence_id'],
                                "licence_level": tournament['judge__member__licence__licence_level']
                            }
                        },
                        "position": {
                            "id": tournament['judge__position_id'],
                            "name": tournament['judge__position__name'],
                            "salary_min": tournament['judge__position__salary_min'],
                            "salary_max": tournament['judge__position__salary_max'],
                            "licence": {
                                "id": tournament['judge__position__licence_id'],
                                "licence_level": tournament['judge__position__licence__licence_level']
                            },
                            "coaching_licence": {
                                "id": tournament['judge__position__coaching_licence_id'],
                                "licence_level": tournament['judge__position__coaching_licence__licence_level']
                            }
                        },
                        "salary": tournament['judge__salary'],
                        "date_employed": tournament['judge__date_employed']
                    }
                }
                data.append(tournament_data)
            return JsonResponse({'tournaments': data}, status=200)

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
