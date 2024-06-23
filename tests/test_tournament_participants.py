from datetime import datetime
from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import TournamentParticipants, Tournaments, Addresses, Employees, Licences, Members, \
    Positions, Groups, Horses, Riders
import json


class TournamentParticipantsViewTests(TestCase):
    def setUp(self):
        self.client = Client()
        self.address = Addresses.objects.create(country='Poland', city='Warsaw', street='Puławska 20', street_no='1',
                                                postal_code='00001')
        self.licence = Licences.objects.create(licence_level="A")
        self.member = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                             date_of_birth=datetime(1990, 1, 1), address=self.address,
                                             phone_number="+1234567890", email="john.doe@example.com", is_active=True,
                                             licence=self.licence)
        self.position = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00,
                                                 licence=self.licence, coaching_licence=self.licence,
                                                 speciality="Jumping")
        self.employee1 = Employees.objects.create(member=self.member, position=self.position, salary=1000,
                                                  date_employed=datetime(1985, 5, 15))
        self.tournament = Tournaments.objects.create(name="Test Tournament", date=datetime(1990, 1, 1), address=self.address, judge=self.employee1)
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")

        self.licence2 = Licences.objects.create(licence_level="Advanced")
        self.member2 = Members.objects.create(
            name="John", surname="Doe", username="johndoe",
            password="password", date_of_birth=datetime(1990, 1, 1),
            phone_number="+1234567890", email="john.doe@example.com",
            is_active=True, address_id=self.address.id, licence_id=self.licence.id
        )

        self.group = Groups.objects.create(name="Beginners", max_group_members=10)

        self.horse = Horses.objects.create(
            breed="Thoroughbred", height=160.0, color="Bay",
            eye_color="Brown", age=8, origin="USA", hairstyle="Short"
        )

        self.rider = Riders.objects.create(
            member=self.member2, parent_consent=True, group=self.group, horse=self.horse
        )

        self.rider2 = Riders.objects.create(
            member=self.member2, parent_consent=True, group=self.group, horse=self.horse
        )

        self.participant = TournamentParticipants.objects.create(tournament=self.tournament, contestant=self.rider,
                                                                 contestant_place=1)

    def test_get_all_tournaments_participants(self):
        url = reverse('get_all_tournaments_participants')
        response = self.client.get(url)

        self.assertEqual(response.status_code, 200)
        self.assertIn('tournament_participants', response.json())

    def test_get_tournament_participant_by_id(self):
        url = reverse('get_tournament_participant_by_id')
        data = {'ids': [self.participant.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertIn('tournament_participants', response.json())

    def test_add_tournament(self):
        url = reverse('add_tournament_participant')
        data = {
            'tournament_participants': [
                {
                    'tournament': {'id': self.tournament.id},
                    'contestant': {'id': self.rider2.id},
                    'contestant_place': 1
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())
        self.assertIn('ids', response.json())
        self.assertTrue(TournamentParticipants.objects.filter(id=response.json()['ids'][0]).exists())

    def test_update_tournament(self):
        url = reverse('update_tournament_participant')
        data = {
            'tournament_participants': [
                {
                    'id': self.participant.id,
                    'tournament': {'id': self.tournament.id},
                    'contestant': {'id': self.rider.id},
                    'contestant_place': 2
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())
        self.assertIn('ids', response.json())

        updated_participant = TournamentParticipants.objects.get(id=self.participant.id)
        self.assertEqual(updated_participant.contestant_place, 2)

    def test_delete_tournament_participant(self):
        url = reverse('delete_tournament_participant')
        data = {'ids': [self.participant.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())
        self.assertIn('deleted_ids', response.json())
        self.assertFalse(TournamentParticipants.objects.filter(id=self.participant.id).exists())
