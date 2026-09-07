# BakalariGuard Architecture

## Application modes

The application uses one APK with two roles:

- Parent mode: manages devices and approvals
- Child mode: locks tablet and enforces homework status

## Layers

```
UI
 |
ViewModel
 |
Repository
 |
Data/API
 |
Storage
```

## Communication

Planned:

- Firebase Cloud Messaging
- Offline command queue
- Signed commands
- Optional local network transport

## Security

Parent commands should be cryptographically signed.
Child devices should never store parent credentials.
