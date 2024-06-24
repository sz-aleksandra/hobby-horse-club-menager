import json
from datetime import datetime

from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Riders, Members, Addresses, Licences, Positions, Groups, Horses, Employees


class DeactivateAccountTestCase(TestCase):

    def setUp(self):
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")

        self.licence = Licences.objects.create(licence_level="Advanced")
        self.member = Members.objects.create(
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
            member=self.member, parent_consent=True, group=self.group, horse=self.horse
        )

        self.member = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                             date_of_birth=datetime(1990, 1, 1), address=self.address,
                                             phone_number="+1234567890", email="john.doe@example.com", is_active=True,
                                             licence=self.licence)

        self.position = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00,
                                                 licence=self.licence, coaching_licence=self.licence,
                                                 speciality="Jumping")

        self.employee = Employees.objects.create(member=self.member, position=self.position, salary=1000,
                                                 date_employed=datetime(1985, 5, 15))

        self.client = Client()

    def test_deactivate_account_valid_id(self):
        url = reverse('deactivate_rider_account')
        data = {'id': self.rider.id}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {'message': 'Successfully deactivated account', 'id': self.rider.id})

        self.rider.refresh_from_db()
        self.assertFalse(self.rider.member.is_active)

    def test_deactivate_account_invalid_id(self):
        url = reverse('deactivate_rider_account')
        data = {'id': 999}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json(), {'error': 'Invalid rider id'})

    def test_deactivate_account_missing_id(self):
        url = reverse('deactivate_rider_account')
        data = {}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json(), {'error': 'No id provided'})

    def test_deactivate_account_invalid_json(self):
        url = reverse('deactivate_rider_account')
        data = 'invalid_json_data'
        response = self.client.post(url, data, content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json(), {'error': 'Invalid JSON'})

    def test_deactivate_employee_account_valid_id(self):
        url = reverse('deactivate_employee_account')
        data = {'id': self.employee.id}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {'message': 'Successfully deactivated account', 'id': self.employee.id})

        self.employee.refresh_from_db()
        self.assertFalse(self.employee.member.is_active)

    def test_deactivate_employee_account_invalid_id(self):
        url = reverse('deactivate_employee_account')
        data = {'id': 999}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json(), {'error': 'Invalid employee id'})

    def test_deactivate_employee_account_missing_id(self):
        url = reverse('deactivate_employee_account')
        data = {}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json(), {'error': 'No id provided'})

    def test_deactivate_employee_account_invalid_json(self):
        url = reverse('deactivate_employee_account')
        data = 'invalid_json_data'
        response = self.client.post(url, data, content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json(), {'error': 'Invalid JSON'})
