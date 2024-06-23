import json
from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Employees, Members, Classes, Groups, Stables, Addresses, Positions, Licences


class GetClassesForEmployeeTestCase(TestCase):

    def setUp(self):
        self.address = Addresses.objects.create(
            country='USA',
            city='New York',
            street='Broadway',
            street_no='123',
            postal_code='10001'
        )
        self.licence = Licences.objects.create(licence_level='A')
        self.licence_advanced = Licences.objects.create(licence_level='Advanced')
        self.licence_basic = Licences.objects.create(licence_level='Basic')
        self.member = Members.objects.create(
            name='John',
            surname='Doe',
            username='johndoe',
            password='password',
            date_of_birth='1990-01-01',
            address=self.address,
            phone_number='+1234567890',
            email='john.doe@example.com',
            is_active=True,
            licence=self.licence
        )

        self.position = Positions.objects.create(
            name='Head Coach',
            salary_min=5000.00,
            salary_max=8000.00,
            licence=self.licence_advanced,
            coaching_licence=self.licence_basic,
            speciality='Jumping'
        )

        self.employee = Employees.objects.create(
            member=self.member,
            position=self.position,
            salary=1000,
            date_employed='1985-05-15'
        )

        self.group = Groups.objects.create(
            name='Jumpers',
            max_group_members=10
        )

        self.stable = Stables.objects.create(
            name='Green Pastures Stables',
            address=self.address
        )

        self.class_ = Classes.objects.create(
            type='Training',
            date='2023-06-15',
            trainer=self.employee,
            group=self.group,
            stable=self.stable
        )

        self.client = Client()

    def test_get_classes_for_employee_valid_id(self):
        # Test retrieving classes for a valid employee ID
        url = reverse('get_classes_for_employee')
        data = {'id': self.employee.id}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        response_data = response.json()
        self.assertIn('classes', response_data)
        classes = response_data['classes']
        self.assertEqual(len(classes), 1)
        class_data = classes[0]

        # Validate class data structure
        self.assertEqual(class_data['id'], self.class_.id)
        self.assertEqual(class_data['type'], self.class_.type)
        self.assertEqual(class_data['date'], '2023-06-15')

        # Validate trainer data
        trainer_data = class_data['trainer']
        self.assertEqual(trainer_data['id'], self.employee.id)
        self.assertEqual(trainer_data['member']['id'], self.member.id)
        self.assertEqual(trainer_data['position']['id'], self.position.id)
        self.assertEqual(trainer_data['salary'], '1000.00')
        self.assertEqual(trainer_data['date_employed'], '1985-05-15')

        # Validate group data
        group_data = class_data['group']
        self.assertEqual(group_data['id'], self.group.id)
        self.assertEqual(group_data['name'], self.group.name)
        self.assertEqual(group_data['max_group_members'], self.group.max_group_members)

        # Validate stable data
        stable_data = class_data['stable']
        self.assertEqual(stable_data['id'], self.stable.id)
        self.assertEqual(stable_data['name'], self.stable.name)
        self.assertEqual(stable_data['address']['id'], self.address.id)
        self.assertEqual(stable_data['address']['country'], self.address.country)
        self.assertEqual(stable_data['address']['city'], self.address.city)
        self.assertEqual(stable_data['address']['street'], self.address.street)
        self.assertEqual(stable_data['address']['street_no'], self.address.street_no)
        self.assertEqual(stable_data['address']['postal_code'], self.address.postal_code)

    def test_get_classes_for_employee_invalid_id(self):
        url = reverse('get_classes_for_employee')
        data = {'id': 999}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json(), {'error': 'Invalid rider id'})

    def test_get_classes_for_employee_missing_id(self):
        url = reverse('get_classes_for_employee')
        data = {}
        response = self.client.post(url, json.dumps(data), content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json(), {'error': 'No id provided'})

    def test_get_classes_for_employee_invalid_json(self):
        url = reverse('get_classes_for_employee')
        data = 'invalid_json_data'
        response = self.client.post(url, data, content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json(), {'error': 'Invalid JSON'})
