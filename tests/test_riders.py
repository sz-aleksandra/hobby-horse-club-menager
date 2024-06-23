from django.test import TestCase
from django.urls import reverse
from datetime import datetime
import json
from horses_database.models import Riders, Members, Groups, Horses, Addresses, Licences

class TestRiders(TestCase):

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

    def test_get_all_riders(self):
        url = reverse('get_all_riders')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        riders_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('riders', riders_data)
        riders = riders_data['riders']
        self.assertEqual(len(riders), 1)

    def test_get_rider_by_id(self):
        url = reverse('get_rider_by_id')
        data = {'ids': [self.rider.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        rider_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('riders', rider_data)
        riders = rider_data['riders']
        self.assertEqual(len(riders), 1)

    def test_add_rider(self):
        url = reverse('add_rider')
        data = {
            'riders': [
                {
                    'member': {
                        'name': 'Jane',
                        'surname': 'Smith',
                        'username': 'janesmith',
                        'password': 'password',
                        'date_of_birth': '1995-05-05',
                        'address': {
                            'country': 'Canada',
                            'city': 'Toronto',
                            'street': 'King Street',
                            'street_no': '456',
                            'postal_code': 'M5V 1J6'
                        },
                        'phone_number': '+9876543210',
                        'email': 'jane.smith@example.com',
                        'is_active': True,
                        'licence': {'id': self.licence.id}
                    },
                    'parent_consent': False,
                    'group': {'id': self.group.id},
                    'horse': {'id': self.horse.id}
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Riders added successfully')
        self.assertIn('ids', response_data)
        new_rider_id = response_data['ids'][0]
        new_rider = Riders.objects.get(id=new_rider_id)
        self.assertEqual(new_rider.member.name, 'Jane')

    def test_update_rider(self):
        url = reverse('update_rider')
        data = {
            'riders': [
                {
                    'id': self.rider.id,
                    'member': {
                        'id': self.member.id,
                        'name': 'Updated John',
                        'surname': 'Doe',
                        'username': 'johndoe',
                        'password': 'password',
                        'date_of_birth': '1990-01-01',
                        'address': {
                            'id': self.address.id,
                            'country': 'USA',
                            'city': 'New York',
                            'street': 'Broadway',
                            'street_no': '123',
                            'postal_code': '10001'
                        },
                        'phone_number': '+1234567890',
                        'email': 'john.doe@example.com',
                        'is_active': True,
                        'licence': {'id': self.licence.id}
                    },
                    'parent_consent': False,
                    'group_id': self.group.id,
                    'horse_id': self.horse.id
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Riders updated successfully')
        self.assertIn('ids', response_data)
        updated_rider_id = response_data['ids'][0]
        updated_rider = Riders.objects.get(id=updated_rider_id)
        self.assertFalse(updated_rider.parent_consent)

    def test_delete_rider(self):
        url = reverse('delete_rider')
        data = {'ids': [self.rider.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Riders deleted successfully')
        self.assertIn('ids', response_data)
        deleted_ids = response_data['ids']
        self.assertIn(self.rider.id, deleted_ids)
        self.assertFalse(Riders.objects.filter(id=self.rider.id).exists())
