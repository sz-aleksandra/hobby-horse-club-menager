from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Stables, Addresses
import json

class StablesViewTestCase(TestCase):

    def setUp(self):
        self.client = Client()

        # Tworzenie przykładowych adresów
        self.address1 = Addresses.objects.create(
            country='United States',
            city='Test City 1',
            street='Test Street 1',
            street_no='123',
            postal_code='10000'
        )

        self.address2 = Addresses.objects.create(
            country='United States',
            city='Test City 2',
            street='Test Street 2',
            street_no='456',
            postal_code='20000'
        )

        # Tworzenie przykładowych stajni z powiązanymi adresami
        self.stable1 = Stables.objects.create(
            name='Test Stable 1',
            address=self.address1
        )

        self.stable2 = Stables.objects.create(
            name='Test Stable 2',
            address=self.address2
        )

    def test_get_all_stables(self):
        response = self.client.get(reverse('get_all_stables'))
        self.assertEqual(response.status_code, 200)
        stables = json.loads(response.content)['stables']
        self.assertEqual(len(stables), 2)

    def test_get_stable_by_id(self):
        data = {'ids': [self.stable1.id, self.stable2.id]}
        response = self.client.post(reverse('get_stable_by_id'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        stables = json.loads(response.content)['stables']
        self.assertEqual(len(stables), 2)

    def test_add_stable(self):
        data = {
            'stables': [
                {
                    'name': 'New Test Stable 1',
                    'address': {
                        'country': 'United States',
                        'city': 'New Test City 1',
                        'street': 'New Test Street 1',
                        'street_no': '789',
                        'postal_code': '30000'
                    }
                },
                {
                    'name': 'New Test Stable 2',
                    'address': {
                        'country': 'United States',
                        'city': 'New Test City 2',
                        'street': 'New Test Street 2',
                        'street_no': '987',
                        'postal_code': '40000'
                    }
                }
            ]
        }
        response = self.client.post(reverse('add_stable'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = json.loads(response.content)
        self.assertEqual(response_data['message'], 'Stables added successfully')
        self.assertEqual(len(response_data['ids']), 2)

        # Sprawdzanie, czy stajnie zostały dodane do bazy danych
        new_stables = Stables.objects.filter(id__in=response_data['ids'])
        self.assertEqual(new_stables.count(), 2)

    def test_update_stable(self):
        data = {
            'stables': [
                {
                    'id': self.stable1.id,
                    'name': 'Updated Test Stable 1',
                    'address': {
                        'id': self.address1.id,
                        'country': 'United States',
                        'city': 'Updated Test City 1',
                        'street': 'Updated Test Street 1',
                        'street_no': '123',
                        'postal_code': '10001'
                    }
                },
                {
                    'id': self.stable2.id,
                    'name': 'Updated Test Stable 2',
                    'address': {
                        'id': self.address2.id,
                        'country': 'United States',
                        'city': 'Updated Test City 2',
                        'street': 'Updated Test Street 2',
                        'street_no': '456',
                        'postal_code': '20001'
                    }
                }
            ]
        }
        response = self.client.post(reverse('update_stable'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content)
        self.assertEqual(response_data['message'], 'Stables updated successfully')
        self.assertEqual(len(response_data['ids']), 2)

        # Sprawdzanie, czy stajnie zostały zaktualizowane w bazie danych
        updated_stable1 = Stables.objects.get(id=self.stable1.id)
        updated_stable2 = Stables.objects.get(id=self.stable2.id)
        self.assertEqual(updated_stable1.name, 'Updated Test Stable 1')
        self.assertEqual(updated_stable2.name, 'Updated Test Stable 2')

    def test_delete_stable(self):
        data = {'ids': [self.stable1.id, self.stable2.id]}
        response = self.client.post(reverse('delete_stable'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content)
        self.assertEqual(response_data['message'], 'Stables deleted successfully')
        self.assertEqual(len(response_data['ids']), 2)
