import json
from django.test import TestCase, Client
from django.urls import reverse
from django.db import transaction
from horses_database.models import Groups


class GroupsViewTestCase(TestCase):
    def setUp(self):
        self.client = Client()

    def test_get_all_groups(self):
        Groups.objects.create(name="Group A", max_group_members=10)
        Groups.objects.create(name="Group B", max_group_members=15)

        url = reverse('get_all_groups')
        response = self.client.get(url)

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('groups' in data)
        self.assertEqual(len(data['groups']), 2)
        self.assertEqual(data['groups'][0]['name'], 'Group A')
        self.assertEqual(data['groups'][1]['max_group_members'], 15)

    def test_get_group_by_id(self):
        group_a = Groups.objects.create(name="Group A", max_group_members=10)
        group_b = Groups.objects.create(name="Group B", max_group_members=15)

        url = reverse('get_group_by_id')
        data = {'ids': [group_a.id, group_b.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('groups' in data)
        self.assertEqual(len(data['groups']), 2)
        self.assertEqual(data['groups'][0]['name'], 'Group A')
        self.assertEqual(data['groups'][1]['max_group_members'], 15)

    def test_add_new_group(self):
        url = reverse('add_group')
        data = {'groups': [{'name': 'Group C', 'max_group_members': 12}, {'name': 'Group D', 'max_group_members': 20}]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 201)
        data = json.loads(response.content)
        self.assertTrue('ids' in data)
        self.assertEqual(len(data['ids']), 2)

        self.assertEqual(Groups.objects.filter(name='Group C').count(), 1)
        self.assertEqual(Groups.objects.filter(name='Group D').count(), 1)

    def test_update_group(self):
        group_a = Groups.objects.create(name="Group A", max_group_members=10)
        group_b = Groups.objects.create(name="Group B", max_group_members=15)

        url = reverse('update_group')
        data = {'groups': [{'id': group_a.id, 'name': 'Updated Group A', 'max_group_members': 12},
                           {'id': group_b.id, 'name': 'Updated Group B', 'max_group_members': 18}]}
        response = self.client.put(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('ids' in data)
        self.assertEqual(len(data['ids']), 2)

        updated_group_a = Groups.objects.get(id=group_a.id)
        updated_group_b = Groups.objects.get(id=group_b.id)
        self.assertEqual(updated_group_a.name, 'Updated Group A')
        self.assertEqual(updated_group_b.max_group_members, 18)

    def test_delete_group(self):
        group_a = Groups.objects.create(name="Group A", max_group_members=10)
        group_b = Groups.objects.create(name="Group B", max_group_members=15)

        url = reverse('delete_group')
        data = {'ids': [group_a.id, group_b.id]}
        response = self.client.delete(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        data = json.loads(response.content)
        self.assertTrue('deleted_ids' in data)
        self.assertEqual(len(data['deleted_ids']), 2)

        self.assertEqual(Groups.objects.filter(id__in=data['deleted_ids']).count(), 0)
