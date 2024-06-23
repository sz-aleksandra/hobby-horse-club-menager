from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import AccessoryTypes
import json

class AccessoryTypesViewTests(TestCase):

    def setUp(self):
        self.client = Client()
        self.accessory_type_1 = AccessoryTypes.objects.create(type_name='Saddles')
        self.accessory_type_2 = AccessoryTypes.objects.create(type_name='Bridles')

    def test_get_all_accessory_types(self):
        response = self.client.get(reverse('get_all_accessory_types'))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'accessory_types': [
                {'id': self.accessory_type_1.id, 'type_name': 'Saddles'},
                {'id': self.accessory_type_2.id, 'type_name': 'Bridles'}
            ]
        })

    def test_get_accessory_types_by_id(self):
        payload = {'ids': [self.accessory_type_1.id, self.accessory_type_2.id]}
        response = self.client.post(reverse('get_accessory_types_by_id'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'accessory_types': [
                {'id': self.accessory_type_1.id, 'type_name': 'Saddles'},
                {'id': self.accessory_type_2.id, 'type_name': 'Bridles'}
            ]
        })

    def test_add_accessory_type(self):
        payload = {
            'accessory_types': [
                {'type_name': 'Stirrups'},
                {'type_name': 'Helmets'}
            ]
        }
        response = self.client.post(reverse('add_accessory_type'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = response.json()
        self.assertEqual(response_data['message'], 'Accessory types added successfully')
        self.assertEqual(len(response_data['ids']), 2)
        self.assertTrue(AccessoryTypes.objects.filter(type_name='Stirrups').exists())
        self.assertTrue(AccessoryTypes.objects.filter(type_name='Helmets').exists())

    def test_update_accessory_type(self):
        payload = {
            'accessory_types': [
                {'id': self.accessory_type_1.id, 'type_name': 'Updated Saddles'},
                {'id': self.accessory_type_2.id, 'type_name': 'Updated Bridles'}
            ]
        }
        response = self.client.post(reverse('update_accessory_type'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'message': 'Accessory types updated successfully',
            'ids': [self.accessory_type_1.id, self.accessory_type_2.id]
        })
        self.accessory_type_1.refresh_from_db()
        self.accessory_type_2.refresh_from_db()
        self.assertEqual(self.accessory_type_1.type_name, 'Updated Saddles')
        self.assertEqual(self.accessory_type_2.type_name, 'Updated Bridles')

    def test_delete_accessory_type(self):
        payload = {'ids': [self.accessory_type_1.id, self.accessory_type_2.id]}
        response = self.client.post(reverse('delete_accessory_type'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'message': 'Accessory types deleted successfully',
            'ids': [self.accessory_type_1.id, self.accessory_type_2.id]
        })
        self.assertFalse(AccessoryTypes.objects.filter(id=self.accessory_type_1.id).exists())
        self.assertFalse(AccessoryTypes.objects.filter(id=self.accessory_type_2.id).exists())
