from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Accessories, AccessoryTypes
import json

class AccessoriesViewTests(TestCase):

    def setUp(self):
        self.client = Client()
        self.accessory_type_1 = AccessoryTypes.objects.create(type_name='Saddles')
        self.accessory_type_2 = AccessoryTypes.objects.create(type_name='Bridles')
        self.accessory_1 = Accessories.objects.create(name='English Saddle', type=self.accessory_type_1)
        self.accessory_2 = Accessories.objects.create(name='Western Bridle', type=self.accessory_type_2)

    def test_get_all_accessories(self):
        response = self.client.get(reverse('get_all_accessories'))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'accessories': [
                {
                    'id': self.accessory_1.id,
                    'name': 'English Saddle',
                    'type': {
                        'id': self.accessory_type_1.id,
                        'type_name': 'Saddles'
                    }
                },
                {
                    'id': self.accessory_2.id,
                    'name': 'Western Bridle',
                    'type': {
                        'id': self.accessory_type_2.id,
                        'type_name': 'Bridles'
                    }
                }
            ]
        })

    def test_get_accessory_by_id(self):
        payload = {
            'accessory_ids': [self.accessory_1.id, self.accessory_2.id]
        }
        response = self.client.post(reverse('get_accessory_by_id'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'accessories': [
                {
                    'id': self.accessory_1.id,
                    'name': 'English Saddle',
                    'type': {
                        'id': self.accessory_type_1.id,
                        'type_name': 'Saddles'
                    }
                },
                {
                    'id': self.accessory_2.id,
                    'name': 'Western Bridle',
                    'type': {
                        'id': self.accessory_type_2.id,
                        'type_name': 'Bridles'
                    }
                }
            ]
        })

    def test_add_new_accessories(self):
        payload = {
            'accessories': [
                {'name': 'Jumping Saddle', 'type_id': self.accessory_type_1.id},
                {'name': 'Dressage Bridle', 'type_id': self.accessory_type_2.id}
            ]
        }
        response = self.client.post(reverse('add_accessory'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = response.json()
        self.assertEqual(response_data['message'], 'Accessories added successfully')
        self.assertEqual(len(response_data['ids']), 2)
        self.assertTrue(Accessories.objects.filter(name='Jumping Saddle').exists())
        self.assertTrue(Accessories.objects.filter(name='Dressage Bridle').exists())

    def test_update_accessories(self):
        payload = {
            'accessories': [
                {'id': self.accessory_1.id, 'name': 'Updated Saddle', 'type_id': self.accessory_type_2.id},
                {'id': self.accessory_2.id, 'name': 'Updated Bridle', 'type_id': self.accessory_type_1.id}
            ]
        }
        response = self.client.post(reverse('update_accessory'), data=json.dumps(payload), content_type='application/json')
        response_data = response.json()
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response_data['message'], 'Accessories updated successfully')
        self.assertEqual(len(response_data['ids']), 2)
        self.accessory_1.refresh_from_db()
        self.accessory_2.refresh_from_db()
        self.assertEqual(self.accessory_1.name, 'Updated Saddle')
        self.assertEqual(self.accessory_1.type, self.accessory_type_2)
        self.assertEqual(self.accessory_2.name, 'Updated Bridle')
        self.assertEqual(self.accessory_2.type, self.accessory_type_1)

    def test_delete_accessories(self):
        payload = {
            'accessory_ids': [self.accessory_1.id, self.accessory_2.id]
        }
        response = self.client.post(reverse('delete_accessory'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'message': 'Accessories deleted successfully',
            'ids': [self.accessory_1.id, self.accessory_2.id]
        })
        self.assertFalse(Accessories.objects.filter(id=self.accessory_1.id).exists())
        self.assertFalse(Accessories.objects.filter(id=self.accessory_2.id).exists())
