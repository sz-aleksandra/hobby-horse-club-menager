import json

from django.db import transaction, IntegrityError, DatabaseError
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from horses_database.models import TournamentParticipants, Tournaments, Riders


class TournamentParticipantsView:
    @staticmethod
    @csrf_exempt
    def get_all_tournaments_participants(request):
        """
        Get all tournament participants.
        Example request JSON: N/A
        Example response JSON:
        {
            "tournament_participants": [
                {
                    "id": 1,
                    "tournament": {
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
                    },
                    "contestant": {
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
                            "height": 170,
                            "color": "Bay",
                            "eye_color": "Brown",
                            "age": 7,
                            "origin": "USA",
                            "hairstyle": "Short"
                        }
                    },
                    "contestant_place": 3
                }
            ]
        }
        """
        try:
            participants = TournamentParticipants.objects.select_related(
                'tournament__address', 'tournament__judge__member__address', 'tournament__judge__member__licence',
                'tournament__judge__position__licence', 'tournament__judge__position__coaching_licence',
                'contestant__member__address' 'contestant__member__licence', 'contestant__group', 'contestant__horse'
            ).values(
                'id', 'tournament_id', 'tournament__name', 'tournament__date', 'tournament__address_id',
                'tournament__address__country',
                'tournament__address__city', 'tournament__address__street', 'tournament__address__street_no',
                'tournament__address__postal_code', 'tournament__judge_id', 'tournament__judge__member_id',
                'tournament__judge__member__name', 'tournament__judge__member__surname',
                'tournament__judge__member__username', 'tournament__judge__member__date_of_birth',
                'tournament__judge__member__address_id', 'tournament__judge__member__address__country',
                'tournament__judge__member__address__city', 'tournament__judge__member__address__street',
                'tournament__judge__member__address__street_no', 'tournament__judge__member__address__postal_code',
                'tournament__judge__member__phone_number', 'tournament__judge__member__email',
                'tournament__judge__member__is_active', 'tournament__judge__member__licence_id',
                'tournament__judge__member__licence__licence_level', 'tournament__judge__position_id',
                'tournament__judge__position__name', 'tournament__judge__position__salary_min',
                'tournament__judge__position__salary_max', 'tournament__judge__position__licence_id',
                'tournament__judge__position__licence__licence_level',
                'tournament__judge__position__coaching_licence_id',
                'tournament__judge__position__coaching_licence__licence_level', 'tournament__judge__salary',
                'tournament__judge__date_employed', 'contestant_id', 'contestant__member_id', 'contestant__member__name',
                'contestant__member__surname',
                'contestant__member__username', 'contestant__member__date_of_birth', 'contestant__member__address_id',
                'contestant__member__address__country',
                'contestant__member__address__city', 'contestant__member__address__street',
                'contestant__member__address__street_no',
                'contestant__member__address__postal_code', 'contestant__member__phone_number',
                'contestant__member__email', 'contestant__member__is_active',
                'contestant__member__licence_id', 'contestant__member__licence__licence_level',
                'contestant__parent_consent', 'contestant__group_id', 'contestant__group__name',
                'contestant__group__max_group_members', 'contestant__horse_id', 'contestant__horse__breed',
                'contestant__horse__height', 'contestant__horse__color', 'contestant__horse__eye_color',
                'contestant__horse__age', 'contestant__horse__origin', 'contestant__horse__hairstyle',
                'contestant_place'
            )

            data = []
            for participant in participants:
                participant_data = {
                    "id": participant['id'],
                    "tournament": {
                        "id": participant['tournament_id'],
                        "name": participant['tournament__name'],
                        "date": participant['tournament__date'],
                        "address": {
                            "id": participant['tournament__address_id'],
                            "country": participant['tournament__address__country'],
                            "city": participant['tournament__address__city'],
                            "street": participant['tournament__address__street'],
                            "street_no": participant['tournament__address__street_no'],
                            "postal_code": participant['tournament__address__postal_code']
                        },
                        "judge": {
                            "id": participant['tournament__judge_id'],
                            "member": {
                                "id": participant['tournament__judge__member_id'],
                                "name": participant['tournament__judge__member__name'],
                                "surname": participant['tournament__judge__member__surname'],
                                "username": participant['tournament__judge__member__username'],
                                "date_of_birth": participant['tournament__judge__member__date_of_birth'],
                                "address": {
                                    "id": participant['tournament__judge__member__address_id'],
                                    "country": participant['tournament__judge__member__address__country'],
                                    "city": participant['tournament__judge__member__address__city'],
                                    "street": participant['tournament__judge__member__address__street'],
                                    "street_no": participant['tournament__judge__member__address__street_no'],
                                    "postal_code": participant['tournament__judge__member__address__postal_code']
                                },
                                "phone_number": participant['tournament__judge__member__phone_number'],
                                "email": participant['tournament__judge__member__email'],
                                "is_active": participant['tournament__judge__member__is_active'],
                                "licence": {
                                    "id": participant['tournament__judge__member__licence_id'],
                                    "licence_level": participant['tournament__judge__member__licence__licence_level']
                                }
                            },
                            "position": {
                                "id": participant['tournament__judge__position_id'],
                                "name": participant['tournament__judge__position__name'],
                                "salary_min": participant['tournament__judge__position__salary_min'],
                                "salary_max": participant['tournament__judge__position__salary_max'],
                                "licence": {
                                    "id": participant['tournament__judge__position__licence_id'],
                                    "licence_level": participant['tournament__judge__position__licence__licence_level']
                                },
                                "coaching_licence": {
                                    "id": participant['tournament__judge__position__coaching_licence_id'],
                                    "licence_level": participant[
                                        'tournament__judge__position__coaching_licence__licence_level']
                                }
                            },
                            "salary": participant['tournament__judge__salary'],
                            "date_employed": participant['tournament__judge__date_employed']
                        }
                    },
                    "contestant": {
                        "id": participant['contestant_id'],
                        "member": {
                            "id": participant['contestant__member_id'],
                            "name": participant['contestant__member__name'],
                            "surname": participant['contestant__member__surname'],
                            "username": participant['contestant__member__username'],
                            "date_of_birth": participant['contestant__member__date_of_birth'],
                            "address": {
                                "id": participant['contestant__member__address_id'],
                                "country": participant['contestant__member__address__country'],
                                "city": participant['contestant__member__address__city'],
                                "street": participant['contestant__member__address__street'],
                                "street_no": participant['contestant__member__address__street_no'],
                                "postal_code": participant['contestant__member__address__postal_code']
                            },
                            "phone_number": participant['contestant__member__phone_number'],
                            "email": participant['contestant__member__email'],
                            "is_active": participant['contestant__member__is_active'],
                            "licence": {
                                "id": participant['contestant__member__licence_id'],
                                "licence_level": participant['contestant__member__licence__licence_level']
                            }
                        },
                        "parent_consent": participant['contestant__parent_consent'],
                        "group": {
                            "id": participant['contestant__group_id'],
                            "name": participant['contestant__group__name'],
                            "max_group_members": participant['contestant__group__max_group_members']
                        },
                        "horse": {
                            "id": participant['contestant__horse_id'],
                            "breed": participant['contestant__horse__breed'],
                            "height": participant['contestant__horse__height'],
                            "color": participant['contestant__horse__color'],
                            "eye_color": participant['contestant__horse__eye_color'],
                            "age": participant['contestant__horse__age'],
                            "origin": participant['contestant__horse__origin'],
                            "hairstyle": participant['contestant__horse__hairstyle']
                        }
                    },
                    "contestant_place": participant['contestant_place']
                }
                data.append(participant_data)
            return JsonResponse({'tournament_participants': data}, status=200)
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
    def get_tournament_participant_by_id(request):
        """
        Example request JSON:
        {
            "ids": [1]
        }

        Example response JSON:
        {
            "tournament_participants": [
                {
                    "id": 1,
                    "tournament": {
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
                    },
                    "contestant": {
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
                            "height": 170,
                            "color": "Bay",
                            "eye_color": "Brown",
                            "age": 7,
                            "origin": "USA",
                            "hairstyle": "Short"
                        }
                    },
                    "contestant_place": 3
                }
            ]
        }
        """
        try:
            data = json.loads(request.body)
            ids = data.get('ids', [])
            if not ids:
                return JsonResponse({'error': 'At least one Tournament Participant ID is required'}, status=400)
            participants = TournamentParticipants.objects.filter(id__in=ids).select_related(
                'tournament__address', 'tournament__judge__member__address', 'tournament__judge__member__licence',
                'tournament__judge__position__licence', 'tournament__judge__position__coaching_licence',
                'contestant__member__address' 'contestant__member__licence', 'contestant__group', 'contestant__horse'
            ).values(
                'id', 'tournament_id', 'tournament__name', 'tournament__date', 'tournament__address_id',
                'tournament__address__country',
                'tournament__address__city', 'tournament__address__street', 'tournament__address__street_no',
                'tournament__address__postal_code', 'tournament__judge_id', 'tournament__judge__member_id',
                'tournament__judge__member__name', 'tournament__judge__member__surname',
                'tournament__judge__member__username', 'tournament__judge__member__date_of_birth',
                'tournament__judge__member__address_id', 'tournament__judge__member__address__country',
                'tournament__judge__member__address__city', 'tournament__judge__member__address__street',
                'tournament__judge__member__address__street_no', 'tournament__judge__member__address__postal_code',
                'tournament__judge__member__phone_number', 'tournament__judge__member__email',
                'tournament__judge__member__is_active', 'tournament__judge__member__licence_id',
                'tournament__judge__member__licence__licence_level', 'tournament__judge__position_id',
                'tournament__judge__position__name', 'tournament__judge__position__salary_min',
                'tournament__judge__position__salary_max', 'tournament__judge__position__licence_id',
                'tournament__judge__position__licence__licence_level',
                'tournament__judge__position__coaching_licence_id',
                'tournament__judge__position__coaching_licence__licence_level', 'tournament__judge__salary',
                'tournament__judge__date_employed', 'contestant_id', 'contestant__member_id', 'contestant__member__name',
                'contestant__member__surname',
                'contestant__member__username', 'contestant__member__date_of_birth', 'contestant__member__address_id',
                'contestant__member__address__country',
                'contestant__member__address__city', 'contestant__member__address__street',
                'contestant__member__address__street_no',
                'contestant__member__address__postal_code', 'contestant__member__phone_number',
                'contestant__member__email', 'contestant__member__is_active',
                'contestant__member__licence_id', 'contestant__member__licence__licence_level',
                'contestant__parent_consent', 'contestant__group_id', 'contestant__group__name',
                'contestant__group__max_group_members', 'contestant__horse_id', 'contestant__horse__breed',
                'contestant__horse__height', 'contestant__horse__color', 'contestant__horse__eye_color',
                'contestant__horse__age', 'contestant__horse__origin', 'contestant__horse__hairstyle',
                'contestant_place'
            )

            data = []
            for participant in participants:
                participant_data = {
                    "id": participant['id'],
                    "tournament": {
                        "id": participant['tournament_id'],
                        "name": participant['tournament__name'],
                        "date": participant['tournament__date'],
                        "address": {
                            "id": participant['tournament__address_id'],
                            "country": participant['tournament__address__country'],
                            "city": participant['tournament__address__city'],
                            "street": participant['tournament__address__street'],
                            "street_no": participant['tournament__address__street_no'],
                            "postal_code": participant['tournament__address__postal_code']
                        },
                        "judge": {
                            "id": participant['tournament__judge_id'],
                            "member": {
                                "id": participant['tournament__judge__member_id'],
                                "name": participant['tournament__judge__member__name'],
                                "surname": participant['tournament__judge__member__surname'],
                                "username": participant['tournament__judge__member__username'],
                                "date_of_birth": participant['tournament__judge__member__date_of_birth'],
                                "address": {
                                    "id": participant['tournament__judge__member__address_id'],
                                    "country": participant['tournament__judge__member__address__country'],
                                    "city": participant['tournament__judge__member__address__city'],
                                    "street": participant['tournament__judge__member__address__street'],
                                    "street_no": participant['tournament__judge__member__address__street_no'],
                                    "postal_code": participant['tournament__judge__member__address__postal_code']
                                },
                                "phone_number": participant['tournament__judge__member__phone_number'],
                                "email": participant['tournament__judge__member__email'],
                                "is_active": participant['tournament__judge__member__is_active'],
                                "licence": {
                                    "id": participant['tournament__judge__member__licence_id'],
                                    "licence_level": participant['tournament__judge__member__licence__licence_level']
                                }
                            },
                            "position": {
                                "id": participant['tournament__judge__position_id'],
                                "name": participant['tournament__judge__position__name'],
                                "salary_min": participant['tournament__judge__position__salary_min'],
                                "salary_max": participant['tournament__judge__position__salary_max'],
                                "licence": {
                                    "id": participant['tournament__judge__position__licence_id'],
                                    "licence_level": participant['tournament__judge__position__licence__licence_level']
                                },
                                "coaching_licence": {
                                    "id": participant['tournament__judge__position__coaching_licence_id'],
                                    "licence_level": participant[
                                        'tournament__judge__position__coaching_licence__licence_level']
                                }
                            },
                            "salary": participant['tournament__judge__salary'],
                            "date_employed": participant['tournament__judge__date_employed']
                        }
                    },
                    "contestant": {
                        "id": participant['contestant_id'],
                        "member": {
                            "id": participant['contestant__member_id'],
                            "name": participant['contestant__member__name'],
                            "surname": participant['contestant__member__surname'],
                            "username": participant['contestant__member__username'],
                            "date_of_birth": participant['contestant__member__date_of_birth'],
                            "address": {
                                "id": participant['contestant__member__address_id'],
                                "country": participant['contestant__member__address__country'],
                                "city": participant['contestant__member__address__city'],
                                "street": participant['contestant__member__address__street'],
                                "street_no": participant['contestant__member__address__street_no'],
                                "postal_code": participant['contestant__member__address__postal_code']
                            },
                            "phone_number": participant['contestant__member__phone_number'],
                            "email": participant['contestant__member__email'],
                            "is_active": participant['contestant__member__is_active'],
                            "licence": {
                                "id": participant['contestant__member__licence_id'],
                                "licence_level": participant['contestant__member__licence__licence_level']
                            }
                        },
                        "parent_consent": participant['contestant__parent_consent'],
                        "group": {
                            "id": participant['contestant__group_id'],
                            "name": participant['contestant__group__name'],
                            "max_group_members": participant['contestant__group__max_group_members']
                        },
                        "horse": {
                            "id": participant['contestant__horse_id'],
                            "breed": participant['contestant__horse__breed'],
                            "height": participant['contestant__horse__height'],
                            "color": participant['contestant__horse__color'],
                            "eye_color": participant['contestant__horse__eye_color'],
                            "age": participant['contestant__horse__age'],
                            "origin": participant['contestant__horse__origin'],
                            "hairstyle": participant['contestant__horse__hairstyle']
                        }
                    },
                    "contestant_place": participant['contestant_place']
                }
                data.append(participant_data)
            return JsonResponse({'tournament_participants': data}, status=200)
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
            "tournament_participants": [
                {
                    "tournament": {
                        "id": 1,
                    },
                    "contestant": {
                        "id": 1
                    },
                    "contestant_place": 3
                }
            ]
        }

        Example JSON response:
        {
            'message': 'Tournament Participants added successfully',
            'ids': [2]
        }
        """
        try:
            data = json.loads(request.body)
            tournament_participants = data.get('tournament_participants', [])

            if not tournament_participants:
                return JsonResponse({'error': 'No tournament participants provided'}, status=400)

            new_tournament_participants_id = []
            with transaction.atomic():
                for tournament_participant_data in tournament_participants:
                    tournament_id = tournament_participant_data.get('tournament', {}).get('id')
                    contestant_id = tournament_participant_data.get('contestant', {}).get('id')
                    contestant_place = tournament_participant_data.get('contestant_place')

                    if not Tournaments.objects.filter(id=tournament_id).exists():
                        return JsonResponse({'error': f'Tournament with ID {tournament_id} does not exist'}, status=400)

                    if not Riders.objects.filter(id=contestant_id).exists():
                        return JsonResponse({'error': f'Rider with ID {contestant_id} does not exist'}, status=400)

                    new_tournament_participant = TournamentParticipants.objects.create(tournament_id=tournament_id,
                                                                                       contestant_id=contestant_id,
                                                                                       contestant_place=contestant_place)
                    new_tournament_participants_id.append(new_tournament_participant.id)

            return JsonResponse(
                {'message': 'Tournament Participants added successfully', 'ids': new_tournament_participants_id},
                status=200)
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
            "tournament_participants": [
                {
                    "id": 1
                    "tournament": {
                        "id": 1,
                    },
                    "contestant": {
                        "id": 1
                    },
                    "contestant_place": 3
                }
            ]
        }

        Example JSON response:
        {
            'message': 'Tournament Participants updated successfully',
            'ids': [2]
        }
        """
        try:
            data = json.loads(request.body)
            tournament_participants = data.get('tournament_participants', [])

            if not tournament_participants:
                return JsonResponse({'error': 'No tournament participants provided'}, status=400)

            updated_ids = []
            with transaction.atomic():
                for tournament_participant_data in tournament_participants:
                    tournament_part_id = tournament_participant_data.get('id')
                    tournament_id = tournament_participant_data.get('tournament', {}).get('id')
                    contestant_id = tournament_participant_data.get('contestant', {}).get('id')
                    contestant_place = tournament_participant_data.get('contestant_place')

                    if not contestant_place:
                        return JsonResponse({'error': 'Contestant place is required'}, status=400)

                    if not Tournaments.objects.filter(id=tournament_id).exists():
                        return JsonResponse({'error': f'Tournament with ID {tournament_id} does not exist'}, status=400)

                    if not Riders.objects.filter(id=contestant_id).exists():
                        return JsonResponse({'error': f'Rider with ID {contestant_id} does not exist'}, status=400)

                    if not TournamentParticipants.objects.filter(id=tournament_part_id).exists():
                        return JsonResponse({'error': f'Tournament Participant with ID {contestant_id} does not exist'},
                                            status=400)

                    new_tournament_participant = TournamentParticipants.objects.filter(id=tournament_part_id).update(
                        tournament_id=tournament_id, contestant_id=contestant_id, contestant_place=contestant_place)
                    updated_ids.append(tournament_part_id)

            return JsonResponse({'message': 'Tournament Participants updated successfully', 'ids': updated_ids},
                                status=200)
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
    def delete_tournament_participant(request):
        """
        Example JSON request:
        {
            'ids': [3, 4]
        }

        Example JSON response:
        {
            'message': 'Tournament participants deleted successfully',
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
                for tournament_participant_id in ids:
                    TournamentParticipants.objects.filter(id=tournament_participant_id).delete()
                    deleted_ids.append(tournament_participant_id)

            return JsonResponse({'message': 'Tournament participants deleted successfully', 'deleted_ids': deleted_ids},
                                status=200)
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
