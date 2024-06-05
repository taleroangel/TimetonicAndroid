package dev.taleroangel.timetonic.infrastructure.exception

class ExpiredTokenException : BadRequest("Token contents expired")