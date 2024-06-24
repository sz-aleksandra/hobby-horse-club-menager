import json
from datetime import datetime

from django.test import TestCase
from django.urls import reverse
from horses_database.models import Riders, Horses, Addresses, Licences, Members, Groups


class AddHorseTestCase(TestCase):

    def setUp(self):
        self.horse = Horses.objects.create(
            breed="Thoroughbred",
            height=170,
            color="Bay",
            eye_color="Brown",
            age=7,
            origin="USA",
            hairstyle="Short"
        )
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
            member_id=self.member.id,
            parent_consent=True
        )

    def test_add_horse_success(self):
        url = reverse('add_horse_to_rider')

        data = {
            'rider_id': self.rider.id,
            'horse_id': self.horse.id
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)

        response_data = json.loads(response.content)
        self.assertEqual(response_data['message'], 'Successfully added horse to rider')
        self.assertEqual(response_data['rider_id'], self.rider.id)

        updated_rider = Riders.objects.get(id=self.rider.id)
        self.assertEqual(updated_rider.horse_id, self.horse.id)

    def test_add_horse_missing_fields(self):
        url = reverse('add_horse_to_rider')

        data = {
            'rider_id': self.rider.id,
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 400)

        response_data = json.loads(response.content)
        self.assertIn('error', response_data)
        self.assertEqual(response_data['error'], 'Rider_id and horse_id must be provided')

    def test_add_horse_invalid_rider_id(self):
        url = reverse('add_horse_to_rider')

        data = {
            'rider_id': 999,
            'horse_id': self.horse.id
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 401)

        response_data = json.loads(response.content)
        self.assertIn('error', response_data)
        self.assertEqual(response_data['error'], 'Invalid rider id')

    def test_add_horse_invalid_horse_id(self):
        url = reverse('add_horse_to_rider')

        data = {
            'rider_id': self.rider.id,
            'horse_id': 999
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 400)

        response_data = json.loads(response.content)
        self.assertIn('error', response_data)
        self.assertEqual(response_data['error'], 'Group with ID 999 does not exist')


    def test_add_to_group_success(self):
        url = reverse('add_group_to_rider')

        data = {
            'rider_id': self.rider.id,
            'group_id': self.group.id
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)

        response_data = json.loads(response.content)
        self.assertEqual(response_data['message'], 'Successfully added rider to group')
        self.assertEqual(response_data['rider_id'], self.rider.id)

        updated_rider = Riders.objects.get(id=self.rider.id)
        self.assertEqual(updated_rider.group_id, self.group.id)

    def test_add_to_group_missing_fields(self):
        url = reverse('add_group_to_rider')

        data = {
            'rider_id': self.rider.id,
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 400)

        response_data = json.loads(response.content)
        self.assertIn('error', response_data)
        self.assertEqual(response_data['error'], 'Rider_id and group_id must be provided')

    def test_add_to_group_invalid_rider_id(self):
        url = reverse('add_group_to_rider')

        data = {
            'rider_id': 999,
            'group_id': self.group.id
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 401)

        response_data = json.loads(response.content)
        self.assertIn('error', response_data)
        self.assertEqual(response_data['error'], 'Invalid rider id')

    def test_add_to_group_invalid_group_id(self):
        url = reverse('add_group_to_rider')

        data = {
            'rider_id': self.rider.id,
            'group_id': 999
        }

        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 400)

        response_data = json.loads(response.content)
        self.assertIn('error', response_data)
        self.assertEqual(response_data['error'], 'Group with ID 999 does not exist')
