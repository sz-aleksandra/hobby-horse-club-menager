from django.test import TestCase
from django.urls import reverse
from django.utils import timezone
from datetime import datetime
import json
from horses_database.models import Employees, Members, Positions, Addresses, Licences


class EmployeesViewTests(TestCase):

    def setUp(self):
        # Create sample data for testing
        # Create an address
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")

        # Create a licence
        self.licence = Licences.objects.create(licence_level="A")

        # Create a member
        self.member = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                             date_of_birth=datetime(1990, 1, 1), address=self.address,
                                             phone_number="+1234567890", email="john.doe@example.com", is_active=True,
                                             licence=self.licence)

        # Create a position
        self.position = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00,
                                                 licence=self.licence, coaching_licence=self.licence,
                                                 speciality="Jumping")

        # Create employees
        self.employee1 = Employees.objects.create(member=self.member, position=self.position, salary=1000,
                                                  date_employed=datetime(1985, 5, 15))
        self.employee2 = Employees.objects.create(member=self.member, position=self.position, salary=1200,
                                                  date_employed=datetime(1986, 6, 16))

    def test_get_all_employees(self):
        url = reverse('get_all_employees')  # Assuming 'get_all_employees' is the URL name or pattern
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        employees_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('employees', employees_data)
        employees = employees_data['employees']
        self.assertEqual(len(employees), 2)  # Assuming you have 2 employees created
        # Add more assertions based on your expected JSON structure and data

    def test_get_employee_by_id(self):
        url = reverse('get_employee_by_id')  # Assuming 'get_employee_by_id' is the URL name or pattern
        data = {'ids': [self.employee1.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        employees_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('employees', employees_data)
        employees = employees_data['employees']
        self.assertEqual(len(employees), 1)  # Expecting one employee in response

    def test_add_employee(self):
        url = reverse('add_employee')  # Assuming 'add_employee' is the URL name or pattern
        data = {
            'employees': [
                {
                    'member': {
                        'name': 'Jane',
                        'surname': 'Smith',
                        'username': 'janesmith',
                        "password": "qwerty",
                        'date_of_birth': '1992-02-02',
                        'address': {
                            'country': 'USA',
                            'city': 'Los Angeles',
                            'street': 'Main St',
                            'street_no': '456',
                            'postal_code': '90001'
                        },
                        'phone_number': '+1987654321',
                        'email': 'jane.smith@example.com',
                        'is_active': True,
                        'licence': {'id': self.licence.id}
                    },
                    'position': {'id': self.position.id},
                    'salary': 1500,
                    'date_employed': timezone.now().strftime('%Y-%m-%d')
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Employees added successfully')
        self.assertIn('ids', response_data)
        new_employee_id = response_data['ids'][0]
        new_employee = Employees.objects.get(id=new_employee_id)
        self.assertEqual(new_employee.member.name, 'Jane')

    def test_update_employee(self):
        url = reverse('update_employee')  # Assuming 'update_employee' is the URL name or pattern
        data = {
            'employees': [
                {
                    'id': self.employee1.id,
                    'member': {
                        'id': self.member.id,
                        'name': 'John Updated',
                        'surname': 'Doe Updated',
                        'username': 'johndoe_updated',
                        "password": "qwerty",
                        'date_of_birth': self.member.date_of_birth.strftime('%Y-%m-%d'),
                        'address': {
                            'id': self.address.id,
                            'country': self.address.country,
                            'city': self.address.city,
                            'street': self.address.street,
                            'street_no': self.address.street_no,
                            'postal_code': self.address.postal_code
                        },
                        'phone_number': self.member.phone_number,
                        'email': self.member.email,
                        'is_active': self.member.is_active,
                        'licence': {'id': self.licence.id}
                    },
                    'position': {'id': self.position.id},
                    'salary': 1200,
                    'date_employed': self.employee1.date_employed.strftime('%Y-%m-%d')
                }
            ]
        }
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertEqual(response.status_code, 200)
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Employees updated successfully')
        self.assertIn('ids', response_data)
        updated_employee_id = response_data['ids'][0]
        updated_employee = Employees.objects.get(id=updated_employee_id)
        self.assertEqual(updated_employee.member.name, 'John Updated')

    def test_delete_employee(self):
        url = reverse('delete_employee')  # Assuming 'delete_employee' is the URL name or pattern
        data = {'ids': [self.employee1.id, self.employee2.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('message', response_data)
        self.assertEqual(response_data['message'], 'Employees deleted successfully')
        self.assertIn('deleted_ids', response_data)
        deleted_ids = response_data['deleted_ids']
        self.assertIn(self.employee1.id, deleted_ids)
        self.assertIn(self.employee2.id, deleted_ids)
        self.assertFalse(Employees.objects.filter(id__in=deleted_ids).exists())

