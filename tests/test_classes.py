from django.test import TestCase
from django.urls import reverse
from django.utils import timezone
from datetime import datetime
import json
from horses_database.models import Classes, Employees, Groups, Stables, Addresses, Licences, Positions, Members


class ClassesViewTests(TestCase):

    def setUp(self):
        # Create sample data for testing
        # Create an address
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")

        # Create a licence
        self.licence = Licences.objects.create(licence_level="A")

        # Create a position
        self.position = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00,
                                                 licence=self.licence, coaching_licence=self.licence,
                                                 speciality="Jumping")

        # Create a member
        self.member = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                             date_of_birth=datetime(1990, 1, 1), address=self.address,
                                             phone_number="+1234567890", email="john.doe@example.com", is_active=True,
                                             licence=self.licence)

        # Create a trainer
        self.trainer = Employees.objects.create(member=self.member, position=self.position, salary=1000,
                                                date_employed=datetime(1985, 5, 15))

        # Create a group
        self.group = Groups.objects.create(name="Jumpers", max_group_members=10)

        # Create a stable
        self.stable_address = Addresses.objects.create(country="United States", city="New York", street="Broadway",
                                                       street_no="123", postal_code="10001")
        self.stable = Stables.objects.create(name="Green Pastures Stables", address=self.stable_address)

        # Create classes
        self.class1 = Classes.objects.create(type="Training", date=timezone.now(), trainer=self.trainer,
                                             group=self.group, stable=self.stable)
        self.class2 = Classes.objects.create(type="Jumping", date=timezone.now(), trainer=self.trainer,
                                             group=self.group, stable=self.stable)

    def test_get_all_classes(self):
        url = reverse('get_all_classes')  # Assuming 'get_all_classes' is the URL name or pattern
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        classes_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('classes', classes_data)
        classes = classes_data['classes']
        self.assertEqual(len(classes), 2)  # Assuming you have 2 classes created
        # Add more assertions based on your expected JSON structure and data

    def test_get_class_by_id(self):
        url = reverse('get_class_by_id')  # Assuming 'get_class_by_id' is the URL name or pattern
        data = {'ids': [self.class1.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        classes_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('classes', classes_data)
        classes = classes_data['classes']
        self.assertEqual(len(classes), 1)  # Expecting one class in response

    def test_add_class(self):
        url = reverse('add_class')  # Assuming 'add_class' is the URL name or pattern
        data = {
            'classes': [
                {
                    'type': 'New Class',
                    'date': timezone.now().strftime('%Y-%m-%d'),
                    'trainer': {'id': self.trainer.id},
                    'group': {'id': self.group.id},
                    'stable': {'id': self.stable.id}
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Classes added successfully')
        self.assertIn('ids', response_data)
        new_class_id = response_data['ids'][0]
        new_class = Classes.objects.get(id=new_class_id)
        self.assertEqual(new_class.type, 'New Class')

    def test_update_class(self):
        url = reverse('update_class')  # Assuming 'update_class' is the URL name or pattern
        data = {
            'classes': [
                {
                    'id': self.class1.id,
                    'type': 'Updated Class',
                    'date': timezone.now().strftime('%Y-%m-%d'),
                    'trainer': {'id': self.trainer.id},
                    'group': {'id': self.group.id},
                    'stable': {'id': self.stable.id}
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Classes updated successfully')
        self.assertIn('ids', response_data)
        updated_class_id = response_data['ids'][0]
        updated_class = Classes.objects.get(id=updated_class_id)
        self.assertEqual(updated_class.type, 'Updated Class')

    def test_delete_class(self):
        url = reverse('delete_class')  # Assuming 'delete_class' is the URL name or pattern
        data = {'ids': [self.class1.id, self.class2.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Classes deleted successfully')
        self.assertIn('deleted_ids', response_data)
        deleted_ids = response_data['deleted_ids']
        self.assertIn(self.class1.id, deleted_ids)
        self.assertIn(self.class2.id, deleted_ids)
        self.assertFalse(Classes.objects.filter(id__in=deleted_ids).exists())
