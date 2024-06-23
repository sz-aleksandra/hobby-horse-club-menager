from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Employees, Members, Positions, Riders, Addresses, Licences, Groups, Horses
import json


class LoginTests(TestCase):
    def setUp(self):
        self.client = Client()
        self.address = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123",
                                                postal_code="10001")
        self.licence = Licences.objects.create(licence_level="Basic")
        self.position = Positions.objects.create(name='Owner', salary_min=1000, salary_max=5000)
        self.position_empl = Positions.objects.create(name='Employee', salary_min=1000, salary_max=5000)

        self.member_employee = Members.objects.create(
            username='employee_user',
            password='employee_pass',
            name='Employee',
            surname='User',
            date_of_birth='1980-01-01',
            address_id=self.address.id,
            licence_id=self.licence.id
        )

        self.member_owner = Members.objects.create(
            username='owner_user',
            password='owner_pass',
            name='Owner',
            surname='User',
            date_of_birth='1970-01-01',
            address_id=self.address.id,
            licence_id=self.licence.id
        )

        self.member_rider = Members.objects.create(
            username='rider_user',
            password='rider_pass',
            name='Rider',
            surname='User',
            date_of_birth='2000-01-01',
            address_id=self.address.id,
            licence_id=self.licence.id
        )

        self.employee = Employees.objects.create(
            member=self.member_employee,
            position_id=self.position_empl.id,
            salary=3000,
            date_employed='2020-01-01'
        )

        self.owner = Employees.objects.create(
            member=self.member_owner,
            position_id=self.position.id,
            salary=4000,
            date_employed='2019-01-01'
        )

        self.group = Groups.objects.create(name="Beginners", max_group_members=10)

        self.horse = Horses.objects.create(
            breed="Thoroughbred", height=160.0, color="Bay",
            eye_color="Brown", age=8, origin="USA", hairstyle="Short"
        )

        self.rider = Riders.objects.create(
            member=self.member_rider, parent_consent=True, group=self.group, horse=self.horse
        )

    def test_login_employee_success(self):
        response = self.client.post(reverse('login_employee'), json.dumps({
            'login': 'employee_user',
            'password': 'employee_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()['id'], self.employee.id)

    def test_login_employee_invalid_login(self):
        response = self.client.post(reverse('login_employee'), json.dumps({
            'login': 'wrong_user',
            'password': 'employee_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid login')

    def test_login_employee_invalid_password(self):
        response = self.client.post(reverse('login_employee'), json.dumps({
            'login': 'employee_user',
            'password': 'wrong_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid password')

    def test_login_owner_success(self):
        response = self.client.post(reverse('login_owner'), json.dumps({
            'login': 'owner_user',
            'password': 'owner_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()['id'], self.owner.id)

    def test_login_owner_invalid_login(self):
        response = self.client.post(reverse('login_owner'), json.dumps({
            'login': 'wrong_user',
            'password': 'owner_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid login')

    def test_login_owner_invalid_password(self):
        response = self.client.post(reverse('login_owner'), json.dumps({
            'login': 'owner_user',
            'password': 'wrong_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid password')

    def test_login_owner_not_owner(self):
        response = self.client.post(reverse('login_owner'), json.dumps({
            'login': 'employee_user',
            'password': 'employee_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid login')

    def test_login_rider_success(self):
        response = self.client.post(reverse('login_rider'), json.dumps({
            'login': 'rider_user',
            'password': 'rider_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()['id'], self.rider.id)

    def test_login_rider_invalid_login(self):
        response = self.client.post(reverse('login_rider'), json.dumps({
            'login': 'wrong_user',
            'password': 'rider_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid login')

    def test_login_rider_invalid_password(self):
        response = self.client.post(reverse('login_rider'), json.dumps({
            'login': 'rider_user',
            'password': 'wrong_pass'
        }), content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid password')
