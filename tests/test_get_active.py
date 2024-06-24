from django.test import TestCase
from django.urls import reverse
from datetime import datetime
import json
from horses_database.models import Riders, Members, Groups, Horses, Addresses, Licences, Positions, Employees


class TestGetActive(TestCase):

    def setUp(self):
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")

        self.licence = Licences.objects.create(licence_level="Advanced")
        self.member = Members.objects.create(
            name="John", surname="Doe", username="johndoe",
            password="password", date_of_birth=datetime(1990, 1, 1),
            phone_number="+1234567890", email="john.doe@example.com",
            is_active=False, address_id=self.address.id, licence_id=self.licence.id
        )

        self.group = Groups.objects.create(name="Beginners", max_group_members=10)

        self.horse = Horses.objects.create(
            breed="Thoroughbred", height=160.0, color="Bay",
            eye_color="Brown", age=8, origin="USA", hairstyle="Short"
        )

        self.rider = Riders.objects.create(
            member=self.member, parent_consent=True, group=self.group, horse=self.horse
        )

        self.member_act = Members.objects.create(
            name="John", surname="Doe", username="johndoe",
            password="password", date_of_birth=datetime(1990, 1, 1),
            phone_number="+1234567890", email="john.doe@example.com",
            is_active=True, address_id=self.address.id, licence_id=self.licence.id
        )

        self.rider_act = Riders.objects.create(
            member=self.member_act, parent_consent=True, group=self.group, horse=self.horse
        )

        self.address_emp = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                    postal_code="10001")

        # Create a licence
        self.licence_emp = Licences.objects.create(licence_level="A")

        # Create a member
        self.member_emp = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                                 date_of_birth=datetime(1990, 1, 1), address=self.address,
                                                 phone_number="+1234567890", email="john.doe@example.com",
                                                 is_active=False,
                                                 licence=self.licence)

        self.member_emp_act = Members.objects.create(name="John", surname="Doe", username="johndoe",
                                                     date_of_birth=datetime(1990, 1, 1), address=self.address,
                                                     phone_number="+1234567890", email="john.doe@example.com",
                                                     is_active=True,
                                                     licence=self.licence)

        # Create a position
        self.position_emp = Positions.objects.create(name="Head Coach", salary_min=5000.00, salary_max=8000.00,
                                                     licence=self.licence, coaching_licence=self.licence,
                                                     speciality="Jumping")

        # Create employees
        self.employee = Employees.objects.create(member=self.member_emp, position=self.position_emp, salary=1000,
                                                 date_employed=datetime(1985, 5, 15))

        self.employee_act = Employees.objects.create(member=self.member_emp_act, position=self.position_emp,
                                                     salary=1000,
                                                     date_employed=datetime(1985, 5, 15))

    def test_get_active_employees(self):
        url = reverse('get_active_employees')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        employees_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('employees', employees_data)
        employees = employees_data['employees']
        self.assertEqual(len(employees), 1)
        self.assertEqual(employees[0]["id"], self.employee_act.id)

    def test_get_active_riders(self):
        url = reverse('get_active_riders')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        employees_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('riders', employees_data)
        riders = employees_data['riders']
        self.assertEqual(len(riders), 1)
        self.assertEqual(riders[0]["id"], self.rider_act.id)
