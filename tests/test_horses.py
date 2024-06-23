from django.test import TestCase, Client
from django.urls import reverse
import json
from horses_database.models import Horses

class TestHorsesView(TestCase):

    def setUp(self):
        self.client = Client()

    def test_get_all_horses(self):
        # Create sample horses
        Horses.objects.create(
            breed='Thoroughbred',
            height='16.00',
            color='Bay',
            eye_color='Brown',
            age=5,
            origin='United States',
            hairstyle='Mane and Tail'
        )
        Horses.objects.create(
            breed='Quarter Horse',
            height='15.10',
            color='Sorrel',
            eye_color='Blue',
            age=8,
            origin='United States',
            hairstyle='Short and Smooth'
        )

        # Test GET request
        response = self.client.get(reverse('get_all_horses'))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()['horses']), 2)  # Check if two horses are returned

    def test_get_horses_by_id(self):
        # Create sample horses
        horse1 = Horses.objects.create(
            breed='Thoroughbred',
            height='16.00',
            color='Bay',
            eye_color='Brown',
            age=5,
            origin='United States',
            hairstyle='Mane and Tail'
        )
        horse2 = Horses.objects.create(
            breed='Quarter Horse',
            height='15.10',
            color='Sorrel',
            eye_color='Blue',
            age=8,
            origin='United States',
            hairstyle='Short and Smooth'
        )

        # Test POST request
        data = {'ids': [horse1.id, horse2.id]}
        response = self.client.post(reverse('get_horse_by_id'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()['horses']), 2)  # Check if two horses are returned

    def test_add_horse(self):
        # Test POST request
        data = {
            'horses': [
                {
                    'breed': 'Arabian',
                    'height': '15.20',
                    'color': 'Grey',
                    'eye_color': 'Dark Brown',
                    'age': 6,
                    'origin': 'Arabian Peninsula',
                    'hairstyle': 'Long and Flowing'
                }
            ]
        }
        response = self.client.post(reverse('add_horse'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        self.assertIn('message', response.json())  # Check if 'message' key is present
        self.assertIn('ids', response.json())  # Check if 'ids' key is present

    def test_update_horse(self):
        # Create a sample horse
        horse = Horses.objects.create(
            breed='Thoroughbred',
            height='16.00',
            color='Bay',
            eye_color='Brown',
            age=5,
            origin='United States',
            hairstyle='Mane and Tail'
        )

        # Test POST request
        data = {
            'horses': [
                {
                    'id': horse.id,
                    'breed': 'Updated Thoroughbred',
                    'height': '16.05',
                    'color': 'Bay',
                    'eye_color': 'Brown',
                    'age': 6,
                    'origin': 'United States',
                    'hairstyle': 'Updated Mane and Tail'
                }
            ]
        }
        response = self.client.post(reverse('update_horse'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())  # Check if 'message' key is present
        self.assertIn('ids', response.json())  # Check if 'ids' key is present

    def test_delete_horse(self):
        # Create a sample horse
        horse = Horses.objects.create(
            breed='Thoroughbred',
            height='16.00',
            color='Bay',
            eye_color='Brown',
            age=5,
            origin='United States',
            hairstyle='Mane and Tail'
        )

        # Test POST request
        data = {'ids': [horse.id]}
        response = self.client.post(reverse('delete_horse'), json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response.json())  # Check if 'message' key is present
        self.assertIn('ids', response.json())  # Check if 'ids' key is present
