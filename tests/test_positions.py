from django.test import TestCase, Client
from django.urls import reverse
import json
from horses_database.models import Positions, Licences

class PositionsViewTest(TestCase):

    def setUp(self):
        self.client = Client()

        self.licence_advanced = Licences.objects.create(licence_level='Advanced')
        self.licence_basic = Licences.objects.create(licence_level='Basic')

        self.position1 = Positions.objects.create(
            name='Head Coach',
            salary_min='5000.00',
            salary_max='8000.00',
            licence=self.licence_advanced,
            coaching_licence=self.licence_basic,
            speciality='Jumping'
        )

        self.position2 = Positions.objects.create(
            name='Trainer',
            salary_min='3000.00',
            salary_max='6000.00',
            licence=self.licence_basic,
            coaching_licence=self.licence_advanced,
            speciality='Dressage'
        )

    def test_get_all_positions(self):
        url = reverse('get_all_positions')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        self.assertTrue('positions' in response.json())

    def test_get_position_by_id(self):
        url = reverse('get_position_by_id')
        data = {'ids': [self.position1.id, self.position2.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertTrue('positions' in response.json())

    def test_add_position(self):
        url = reverse('add_position')
        data = {
            'positions': [
                {
                    'name': 'New Position',
                    'salary_min': '4000.00',
                    'salary_max': '7000.00',
                    'licence': {'id': self.licence_advanced.id},
                    'coaching_licence': {'id': self.licence_basic.id},
                    'speciality': 'New Speciality'
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        self.assertTrue('message' in response.json())
        self.assertTrue('ids' in response.json())

    def test_update_position(self):
        url = reverse('update_position')
        data = {
            'positions': [
                {
                    'id': self.position1.id,
                    'name': 'Updated Position',
                    'salary_min': '4500.00',
                    'salary_max': '7500.00',
                    'licence': {'id': self.licence_advanced.id},
                    'coaching_licence': {'id': self.licence_basic.id},
                    'speciality': 'Updated Speciality'
                }
            ]
        }
        response = self.client.put(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertTrue('message' in response.json())
        self.assertTrue('ids' in response.json())

    def test_delete_positions(self):
        url = reverse('delete_positions')
        data = {'ids': [self.position1.id, self.position2.id]}
        response = self.client.delete(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertTrue('message' in response.json())
        self.assertTrue('ids' in response.json())
