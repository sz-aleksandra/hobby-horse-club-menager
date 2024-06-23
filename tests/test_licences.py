from django.test import TestCase
from django.urls import reverse
import json
from horses_database.models import Licences


class LicencesViewTests(TestCase):

    def setUp(self):
        # Create sample data for testing
        self.licence1 = Licences.objects.create(licence_level='Basic')
        self.licence2 = Licences.objects.create(licence_level='Advanced')

    def test_get_all_licences(self):
        url = reverse('get_all_licences')  # Assuming 'get_all_licences' is the URL name or pattern
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        licences_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('licences', licences_data)
        licences = licences_data['licences']
        self.assertEqual(len(licences), 2)  # Assuming you have 2 licences created
        # Add more assertions based on your expected JSON structure and data

    def test_get_licence_by_id(self):
        url = reverse('get_licence_by_id')  # Assuming 'get_licence_by_id' is the URL name or pattern
        data = {'ids': [self.licence1.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        licences_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('licences', licences_data)
        licences = licences_data['licences']
        self.assertEqual(len(licences), 1)  # Expecting one licence in response

    def test_add_new_licence(self):
        url = reverse('add_licence')  # Assuming 'add_new_licence' is the URL name or pattern
        data = {
            'licences': [
                {'licence_level': 'New Licence 1'},
                {'licence_level': 'New Licence 2'}
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 201)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Licences added successfully')
        self.assertIn('ids', response_data)
        new_licence_ids = response_data['ids']
        self.assertEqual(len(new_licence_ids), 2)  # Assuming 2 licences were added

    def test_update_licence(self):
        url = reverse('update_licence')  # Assuming 'update_licence' is the URL name or pattern
        data = {
            'licences': [
                {'id': self.licence1.id, 'licence_level': 'Updated Licence 1'},
                {'id': self.licence2.id, 'licence_level': 'Updated Licence 2'}
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Licences updated successfully')
        self.assertIn('ids', response_data)
        updated_licence_ids = response_data['ids']
        self.assertEqual(len(updated_licence_ids), 2)  # Assuming 2 licences were updated

    def test_delete_licence(self):
        url = reverse('delete_licence')  # Assuming 'delete_licence' is the URL name or pattern
        data = {'ids': [self.licence1.id, self.licence2.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Licences deleted successfully')
        self.assertIn('ids', response_data)
        deleted_ids = response_data['ids']
        self.assertIn(self.licence1.id, deleted_ids)
        self.assertIn(self.licence2.id, deleted_ids)
        self.assertFalse(Licences.objects.filter(id__in=deleted_ids).exists())
