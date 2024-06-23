from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Addresses
import json

class AddressesViewTests(TestCase):

    def setUp(self):
        self.client = Client()
        self.address_1 = Addresses.objects.create(country='Poland', city='Warsaw', street='Puławska 20', street_no='1', postal_code='00001')
        self.address_2 = Addresses.objects.create(country='Poland', city='Kraków', street='Rynek Główny 10', street_no='2', postal_code='00002')
        self.address_3 = Addresses.objects.create(country='USA', city='New York', street='Broadway', street_no='3', postal_code='00003')

    def test_get_all_addresses(self):
        response = self.client.get(reverse('get_all_addresses'))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'addresses': [
                {
                    'id': self.address_1.id,
                    'country': 'Poland',
                    'city': 'Warsaw',
                    'street': 'Puławska 20',
                    'street_no': '1',
                    'postal_code': '00001'
                },
                {
                    'id': self.address_2.id,
                    'country': 'Poland',
                    'city': 'Kraków',
                    'street': 'Rynek Główny 10',
                    'street_no': '2',
                    'postal_code': '00002'
                },
                {
                    'id': self.address_3.id,
                    'country': 'USA',
                    'city': 'New York',
                    'street': 'Broadway',
                    'street_no': '3',
                    'postal_code': '00003'
                }
            ]
        })

    def test_get_address_by_id(self):
        payload = {'ids': [self.address_1.id, self.address_2.id]}
        response = self.client.post(reverse('get_address_by_id'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'addresses': [
                {
                    'id': self.address_1.id,
                    'country': 'Poland',
                    'city': 'Warsaw',
                    'street': 'Puławska 20',
                    'street_no': '1',
                    'postal_code': '00001'
                },
                {
                    'id': self.address_2.id,
                    'country': 'Poland',
                    'city': 'Kraków',
                    'street': 'Rynek Główny 10',
                    'street_no': '2',
                    'postal_code': '00002'
                }
            ]
        })

    def test_get_all_addresses_in_country(self):
        payload = {'countries': ['Poland', 'USA']}
        response = self.client.post(reverse('get_all_addresses_in_country'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'addresses': [
                {
                    'id': self.address_1.id,
                    'country': 'Poland',
                    'city': 'Warsaw',
                    'street': 'Puławska 20',
                    'street_no': '1',
                    'postal_code': '00001'
                },
                {
                    'id': self.address_2.id,
                    'country': 'Poland',
                    'city': 'Kraków',
                    'street': 'Rynek Główny 10',
                    'street_no': '2',
                    'postal_code': '00002'
                },
                {
                    'id': self.address_3.id,
                    'country': 'USA',
                    'city': 'New York',
                    'street': 'Broadway',
                    'street_no': '3',
                    'postal_code': '00003'
                }
            ]
        })

    def test_get_all_addresses_in_city(self):
        payload = {'city': 'Warsaw'}
        response = self.client.post(reverse('get_all_addresses_in_city'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'addresses': [
                {
                    'id': self.address_1.id,
                    'country': 'Poland',
                    'city': 'Warsaw',
                    'street': 'Puławska 20',
                    'street_no': '1',
                    'postal_code': '00001'
                }
            ]
        })

    def test_get_all_addresses_in_street(self):
        payload = {'street': 'Puławska 20'}
        response = self.client.post(reverse('get_all_addresses_in_street'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {
            'addresses': [
                {
                    'id': self.address_1.id,
                    'country': 'Poland',
                    'city': 'Warsaw',
                    'street': 'Puławska 20',
                    'street_no': '1',
                    'postal_code': '00001'
                }
            ]
        })

    def test_add_addresses(self):
        payload = {
            'addresses': [
                {'country': 'Poland', 'city': 'Gdańsk', 'street': 'Długa 5', 'street_no': '10', 'postal_code': '80-827'},
                {'country': 'Germany', 'city': 'Berlin', 'street': 'Alexanderplatz', 'street_no': '1', 'postal_code': '10178'}
            ]
        }
        response = self.client.post(reverse('add_address'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = response.json()
        self.assertEqual(response_data['message'], 'Addresses added successfully')
        self.assertEqual(len(response_data['ids']), 2)
        self.assertTrue(Addresses.objects.filter(country='Poland', city='Gdańsk').exists())
        self.assertTrue(Addresses.objects.filter(country='Germany', city='Berlin').exists())

    def test_update_addresses(self):
        payload = {
            'addresses': [
                {'id': self.address_1.id, 'country': 'Poland', 'city': 'Warsaw', 'street': 'Nowy Świat', 'street_no': '15', 'postal_code': '00-029'},
                {'id': self.address_3.id, 'country': 'USA', 'city': 'Los Angeles', 'street': 'Sunset Blvd', 'street_no': '1234', 'postal_code': '90069'}
            ]
        }
        response = self.client.post(reverse('update_address'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = response.json()
        self.assertEqual(response_data['message'], 'Addresses updated successfully')
        self.assertEqual(len(response_data['ids']), 2)
        self.address_1.refresh_from_db()
        self.assertEqual(self.address_1.street, 'Nowy Świat')
        self.address_3.refresh_from_db()
        self.assertEqual(self.address_3.city, 'Los Angeles')

    def test_delete_addresses(self):
        payload = {'ids': [self.address_1.id, self.address_2.id]}
        response = self.client.post(reverse('delete_address'), data=json.dumps(payload), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = response.json()
        self.assertEqual(response_data['message'], 'Addresses deleted successfully')
        self.assertEqual(response_data['ids'], [self.address_1.id, self.address_2.id])
        self.assertFalse(Addresses.objects.filter(id=self.address_1.id).exists())
        self.assertFalse(Addresses.objects.filter(id=self.address_2.id).exists())
