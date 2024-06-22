from django.db import models

class Addresses(models.Model):
    country = models.CharField(max_length=255)
    city = models.CharField(max_length=255)
    street = models.CharField(max_length=255)
    street_no = models.CharField(max_length=50)
    postal_code = models.CharField(max_length=20)

    class Meta:
        db_table = 'Addresses'


class Licences(models.Model):
    licence_level = models.CharField(max_length=255)

    class Meta:
        db_table = 'Licences'


class Groups(models.Model):
    name = models.CharField(max_length=255)
    max_group_members = models.IntegerField()

    class Meta:
        db_table = 'Groups'


class AccessoryTypes(models.Model):
    type_name = models.CharField(max_length=255, null=False)

    class Meta:
        db_table = 'AccessoryTypes'


class Accessories(models.Model):
    name = models.CharField(max_length=255, null=False)
    type = models.ForeignKey(AccessoryTypes, on_delete=models.RESTRICT)

    class Meta:
        db_table = 'Accessories'


class Horses(models.Model):
    breed = models.CharField(max_length=255)
    height = models.DecimalField(max_digits=5, decimal_places=2)
    color = models.CharField(max_length=50)
    eye_color = models.CharField(max_length=50)
    age = models.IntegerField()
    origin = models.CharField(max_length=255)
    hairstyle = models.CharField(max_length=255)

    class Meta:
        db_table = 'Horses'


class HorseAccessories(models.Model):
    horse = models.ForeignKey(Horses, on_delete=models.CASCADE)
    accessory = models.ForeignKey(Accessories, on_delete=models.CASCADE)

    class Meta:
        db_table = 'Horses_Accessories'


class Stables(models.Model):
    name = models.CharField(max_length=255)
    address = models.ForeignKey(Addresses, on_delete=models.RESTRICT)

    class Meta:
        db_table = 'Stables'


class Positions(models.Model):
    name = models.CharField(max_length=255)
    salary_min = models.DecimalField(max_digits=10, decimal_places=2)
    salary_max = models.DecimalField(max_digits=10, decimal_places=2)
    licence = models.ForeignKey(Licences, on_delete=models.RESTRICT, related_name='positions')
    coaching_licence = models.ForeignKey(Licences, on_delete=models.RESTRICT, related_name='coaching_positions')
    speciality = models.CharField(max_length=255)

    class Meta:
        db_table = 'Positions'


class Members(models.Model):
    name = models.CharField(max_length=255)
    surname = models.CharField(max_length=255)
    username = models.CharField(max_length=255)
    password = models.CharField(max_length=255)
    date_of_birth = models.DateField()
    address = models.ForeignKey(Addresses, on_delete=models.RESTRICT)
    phone_number = models.CharField(max_length=20, null=True, blank=True)
    email = models.CharField(max_length=255, null=True, blank=True)
    is_active = models.BooleanField(default=True)
    licence = models.ForeignKey(Licences, on_delete=models.RESTRICT, related_name='members')

    class Meta:
        db_table = 'Members'


class Riders(models.Model):
    member = models.ForeignKey(Members, on_delete=models.CASCADE)
    parent_consent = models.BooleanField()
    group = models.ForeignKey(Groups, on_delete=models.RESTRICT)
    horse = models.ForeignKey(Horses, on_delete=models.RESTRICT)

    class Meta:
        db_table = 'Riders'


class Employees(models.Model):
    member = models.ForeignKey(Members, on_delete=models.CASCADE)
    position = models.ForeignKey(Positions, on_delete=models.RESTRICT)
    salary = models.DecimalField(max_digits=10, decimal_places=2)
    date_employed = models.DateField()

    class Meta:
        db_table = 'Employees'


class PositionHistory(models.Model):
    employee = models.ForeignKey(Employees, on_delete=models.CASCADE)
    position = models.ForeignKey(Positions, on_delete=models.CASCADE)
    date_start = models.DateField()
    date_end = models.DateField(null=True, blank=True)

    class Meta:
        db_table = 'Positions_History'


class Classes(models.Model):
    type = models.CharField(max_length=255)
    date = models.DateField()
    trainer = models.ForeignKey(Employees, on_delete=models.RESTRICT, related_name='classes')
    group = models.ForeignKey(Groups, on_delete=models.CASCADE)
    stable = models.ForeignKey(Stables, on_delete=models.CASCADE)

    class Meta:
        db_table = 'Classes'


class Tournaments(models.Model):
    name = models.CharField(max_length=255)
    address = models.ForeignKey(Addresses, on_delete=models.RESTRICT)
    judge = models.ForeignKey(Employees, on_delete=models.RESTRICT)

    class Meta:
        db_table = 'Tournaments'


class TournamentParticipants(models.Model):
    tournament = models.ForeignKey(Tournaments, on_delete=models.CASCADE)
    contestant = models.ForeignKey(Members, on_delete=models.CASCADE)
    contestant_place = models.IntegerField(null=True, blank=True)

    class Meta:
        db_table = 'Tournament_Participants'
