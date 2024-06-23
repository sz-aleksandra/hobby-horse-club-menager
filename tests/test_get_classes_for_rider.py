from django.test import TestCase, Client
from django.urls import reverse
from horses_database.models import Riders, Classes, Employees, Members, Positions, Groups, Stables, Addresses, Licences, Horses
from datetime import date


class GetClassesForRiderTestCase(TestCase):
    def setUp(self):
        self.address = Addresses.objects.create(
            country='United States',
            city='New York',
            street='Broadway',
            street_no='123',
            postal_code='10001'
        )

        self.licence_advanced = Licences.objects.create(licence_level='Advanced')
        self.licence_basic = Licences.objects.create(licence_level='Basic')

        self.position_trainer = Positions.objects.create(
            name='Head Coach',
            salary_min=5000.00,
            salary_max=8000.00,
            licence=self.licence_advanced,
            coaching_licence=self.licence_basic,
            speciality='Jumping'
        )

        self.member_trainer = Members.objects.create(
            name='John',
            surname='Doe',
            username='johndoe',
            password='password',
            date_of_birth=date(1990, 1, 1),
            address=self.address,
            phone_number='+1234567890',
            email='john.doe@example.com',
            licence=self.licence_advanced
        )

        self.employee_trainer = Employees.objects.create(
            member=self.member_trainer,
            position=self.position_trainer,
            salary=1000.00,
            date_employed=date(1985, 5, 15)
        )

        self.group = Groups.objects.create(
            name='Jumpers',
            max_group_members=10
        )

        self.stable = Stables.objects.create(
            name='Green Pastures Stables',
            address=self.address
        )
        self.horse = Horses.objects.create(
            breed='Thoroughbred',
            height='16.00',
            color='Bay',
            eye_color='Brown',
            age=5,
            origin='United States',
            hairstyle='Mane and Tail'
        )
        self.rider = Riders.objects.create(
            member=self.member_trainer,
            parent_consent=True,
            group=self.group,
            horse=self.horse
        )

        self.class_ = Classes.objects.create(
            type='Training',
            date=date(2023, 6, 15),
            trainer=self.employee_trainer,
            group=self.group,
            stable=self.stable
        )

    def test_get_classes_for_rider_success(self):
        client = Client()
        url = reverse('get_classes_for_rider')
        data = {'id': self.rider.id}
        response = client.post(url, data, content_type='application/json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()['classes']), 1)

        class_data = response.json()['classes'][0]
        self.assertEqual(class_data['id'], self.class_.id)
        self.assertEqual(class_data['type'], self.class_.type)
        self.assertEqual(class_data['date'], str(self.class_.date))
        self.assertEqual(class_data['trainer']['id'], self.employee_trainer.id)
        self.assertEqual(class_data['trainer']['member']['id'], self.member_trainer.id)
        self.assertEqual(class_data['group']['id'], self.group.id)
        self.assertEqual(class_data['stable']['id'], self.stable.id)

    def test_get_classes_for_rider_no_id(self):
        client = Client()
        url = reverse('get_classes_for_rider')
        data = {}
        response = client.post(url, data, content_type='application/json')

        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json()['error'], 'No id provided')

    def test_get_classes_for_rider_invalid_id(self):
        client = Client()
        url = reverse('get_classes_for_rider')
        data = {'id': 999}
        response = client.post(url, data, content_type='application/json')

        self.assertEqual(response.status_code, 401)
        self.assertEqual(response.json()['error'], 'Invalid rider id')
