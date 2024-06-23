import json
from datetime import datetime

from django.test import TestCase, Client
from horses_database.models import Tournaments, Addresses, Employees, Licences, Members, Positions


class TournamentsViewTests(TestCase):
    def setUp(self):
        self.client = Client()
        self.address = Addresses.objects.create(country='USA', city='Los Angeles', street='1st street', street_no='1',
                                                postal_code='12345')
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")
        self.licence = Licences.objects.create(licence_level="A")
        self.member = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                             date_of_birth=datetime(1990, 1, 1), address=self.address,
                                             phone_number="+1234567890", email="john.doe@example.com", is_active=True,
                                             licence=self.licence)
        self.position = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00,
                                                 licence=self.licence, coaching_licence=self.licence,
                                                 speciality="Jumping")
        self.employee = Employees.objects.create(member=self.member, position=self.position, salary=1000,
                                                 date_employed=datetime(1985, 5, 15))
        self.tournament = Tournaments.objects.create(name='Test Tournament', address=self.address, judge=self.employee)
        self.sample_tournament_data = {
            "tournaments": [
                {
                    "name": "Test Tournament",
                    "date": "1990-01-01",
                    "address": {
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "judge": {
                        "id": self.employee.id
                    }
                }
            ]
        }

    def test_get_all_tournaments(self):
        response = self.client.get('/tournaments/')
        self.assertEqual(response.status_code, 200)
        self.assertIn('tournaments', response.json())
        self.assertEqual(len(response.json()['tournaments']), 1)

    def test_add_tournament(self):
        response = self.client.post('/tournaments/add/', json.dumps(self.sample_tournament_data),
                                    content_type='application/json')
        self.assertEqual(response.status_code, 201)
        self.assertIn('message', response.json())
        self.assertIn('ids', response.json())
        self.assertTrue(Tournaments.objects.filter(id=response.json()['ids'][0]).exists())

    def test_update_tournament(self):
        update_data = self.sample_tournament_data.copy()
        update_data['tournaments'][0]['name'] = "New name"
        update_data['tournaments'][0]['id'] = self.tournament.id
        update_data['tournaments'][0]['address']['id'] = self.address.id
        response = self.client.post(f'/tournaments/update/', json.dumps(update_data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())
        self.assertIn('ids', response.json())

        updated_tournament = Tournaments.objects.get(id=self.tournament.id)
        self.assertEqual(updated_tournament.name, 'New name')

    def test_delete_tournament(self):
        delete_data = {'ids': [self.tournament.id]}
        response = self.client.post('/tournaments/delete/', json.dumps(delete_data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())
        self.assertIn('ids', response.json())
        self.assertFalse(Tournaments.objects.filter(id=self.tournament.id).exists())
