package com.example.noteapp.exceptions

class LocationIsDisabledException : Exception("Location is disabled")
class NoLocationPermissionException : Exception("No location permission granted")

class UnknownLocationErrorException : Exception("couldn't get your current location")