import json
from django.test import TestCase, Client
from django.urls import reverse
from django.db import transaction
from horses_database.models import Horses, Accessories, AccessoryTypes, HorsesAccessories

class HorsesAccessoriesViewTestCase(TestCase):
    def setUp(self):
        self.client = Client()
        self.horse = Horses.objects.create(
            breed="Thoroughbred",
            height=16.00,
            color="Bay",
            eye_color="Brown",
            age=5,
            origin="United States",
            hairstyle="Mane and Tail"
        )
        self.accessory_type = AccessoryTypes.objects.create(type_name="Saddles")
        self.accessory = Accessories.objects.create(
            name="English Saddle",
            type=self.accessory_type
        )
        self.horses_accessory = HorsesAccessories.objects.create(
            horse=self.horse,
            accessory=self.accessory
        )

    def test_get_all_horses_accessories(self):
        url = reverse('get_all_horses_accessories')
        response = self.client.get(url)

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('horses_accessories' in data)
        self.assertEqual(len(data['horses_accessories']), 1)
        self.assertEqual(data['horses_accessories'][0]['id'], self.horses_accessory.id)
        self.assertEqual(data['horses_accessories'][0]['horse']['id'], self.horse.id)
        self.assertEqual(data['horses_accessories'][0]['accessory']['id'], self.accessory.id)
        self.assertEqual(data['horses_accessories'][0]['accessory']['type']['id'], self.accessory_type.id)

    def test_get_horses_accessories_by_id(self):
        url = reverse('get_horses_accessory_by_id')
        data = {'ids': [self.horses_accessory.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('horses_accessories' in data)
        self.assertEqual(len(data['horses_accessories']), 1)
        self.assertEqual(data['horses_accessories'][0]['id'], self.horses_accessory.id)
        self.assertEqual(data['horses_accessories'][0]['horse']['id'], self.horse.id)
        self.assertEqual(data['horses_accessories'][0]['accessory']['id'], self.accessory.id)
        self.assertEqual(data['horses_accessories'][0]['accessory']['type']['id'], self.accessory_type.id)

    def test_add_horses_accessory(self):
        url = reverse('add_horses_accessory')
        new_horse = Horses.objects.create(
            breed="Arabian",
            height=15.50,
            color="Chestnut",
            eye_color="Brown",
            age=4,
            origin="Arabian Peninsula",
            hairstyle="Mane and Tail"
        )
        new_accessory = Accessories.objects.create(
            name="Western Saddle",
            type=self.accessory_type
        )
        data = {
            'horses_accessories': [
                {'horse': {'id': new_horse.id}, 'accessory': {'id': new_accessory.id}}
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 201)
        data = json.loads(response.content)
        self.assertTrue('ids' in data)
        self.assertEqual(len(data['ids']), 1)

        created_horses_accessory = HorsesAccessories.objects.get(id=data['ids'][0])
        self.assertEqual(created_horses_accessory.horse.id, new_horse.id)
        self.assertEqual(created_horses_accessory.accessory.id, new_accessory.id)

    def test_update_horse_accessory(self):
        url = reverse('update_horses_accessory')
        updated_horse = Horses.objects.create(
            breed="Quarter Horse",
            height=15.75,
            color="Palomino",
            eye_color="Brown",
            age=6,
            origin="United States",
            hairstyle="Mane and Tail"
        )
        updated_accessory = Accessories.objects.create(
            name="Western Saddle",
            type=self.accessory_type
        )
        data = {
            'horses_accessories': [
                {'id': self.horses_accessory.id, 'horse': {'id': updated_horse.id}, 'accessory': {'id': updated_accessory.id}}
            ]
        }
        response = self.client.put(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 201)
        data = json.loads(response.content)
        self.assertTrue('ids' in data)
        self.assertEqual(len(data['ids']), 1)

        updated_horses_accessory = HorsesAccessories.objects.get(id=self.horses_accessory.id)
        self.assertEqual(updated_horses_accessory.horse.id, updated_horse.id)
        self.assertEqual(updated_horses_accessory.accessory.id, updated_accessory.id)

    def test_delete_horses_accessory(self):
        url = reverse('delete_horses_accessory')
        data = {'accessory_ids': [self.horses_accessory.id]}
        response = self.client.delete(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('ids' in data)
        self.assertEqual(len(data['ids']), 1)

        with self.assertRaises(HorsesAccessories.DoesNotExist):
            HorsesAccessories.objects.get(id=self.horses_accessory.id)
