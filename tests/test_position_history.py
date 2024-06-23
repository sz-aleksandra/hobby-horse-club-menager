from django.test import TestCase, Client
from django.urls import reverse
import json
from horses_database.models import PositionsHistory, Employees, Members, Addresses, Positions, Licences


class PositionsHistoryViewTests(TestCase):

    def setUp(self):
        self.client = Client()
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123", postal_code="10001")
        self.licence = Licences.objects.create(licence_level="Advanced")
        self.coaching_licence = Licences.objects.create(licence_level="Basic")
        self.member = Members.objects.create(name="John", surname="Doe", username="johndoe", password="secure_password", date_of_birth="1990-01-01", address=self.address, phone_number="+1234567890", email="john.doe@example.com", is_active=True, licence=self.licence)
        self.position = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00, licence=self.licence, coaching_licence=self.coaching_licence, speciality="Jumping")
        self.employee = Employees.objects.create(member=self.member, position=self.position, salary=6000, date_employed="1990-01-01")
        self.position_history = PositionsHistory.objects.create(employee=self.employee, position=self.position, date_start="1990-01-01", date_end="1995-01-01")

    def test_get_all_positions_history(self):
        url = reverse('get_all_positions_history')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        positions_history_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('positions_history', positions_history_data)
        positions_history = positions_history_data['positions_history']
        self.assertEqual(len(positions_history), 1)

    def test_get_position_history_by_id(self):
        url = reverse('get_position_history_by_id')
        data = {'ids': [self.position_history.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        positions_history_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('positions_history', positions_history_data)
        positions_history = positions_history_data['positions_history']
        self.assertEqual(len(positions_history), 1)

    def test_add_position_history(self):
        url = reverse('add_position_history')
        new_position_history_data = {
            "positions_history": [
                {
                    "employee": {
                        "id": self.employee.id
                    },
                    "position": {
                        "id": self.position.id
                    },
                    "date_start": "2000-01-01",
                    "date_end": "2010-01-01"
                }
            ]
        }
        response = self.client.post(url, json.dumps(new_position_history_data), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('ids', response_data)
        new_position_history_id = response_data['ids'][0]
        # Ensure the new position history is created
        self.assertTrue(PositionsHistory.objects.filter(id=new_position_history_id).exists())

    def test_update_position_history(self):
        url = reverse('update_position_history')
        updated_position_history_data = {
            "positions_history": [
                {
                    "id": self.position_history.id,
                    "employee": {
                        "id": self.employee.id
                    },
                    "position": {
                        "id": self.position.id
                    },
                    "date_start": "1990-01-01",
                    "date_end": "2000-01-01"
                }
            ]
        }
        response = self.client.post(url, json.dumps(updated_position_history_data), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('ids', response_data)
        updated_position_history_id = response_data['ids'][0]
        self.assertEqual(updated_position_history_id, self.position_history.id)

    def test_delete_position_history(self):
        url = reverse('delete_position_history')
        data = {'ids': [self.position_history.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('ids', response_data)
        deleted_position_history_id = response_data['ids'][0]
        # Ensure the position history is deleted
        self.assertFalse(PositionsHistory.objects.filter(id=deleted_position_history_id).exists())
