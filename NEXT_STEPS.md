# BakalariGuard - Next implementation steps

## Milestone 0.1 stabilization

- Verify Gradle / Android plugin compatibility
- Verify Room schema and migrations
- Verify AndroidManifest permissions and receivers
- Add real Parent/Child UI flows
- Add local command queue processing
- Add Device Owner integration tests

## Milestone 0.2 connectivity

- Implement Bakalari API client
- Add authentication/session handling
- Add offline cache
- Add parent-child synchronization

## Development requirements

The project should build on Termux/Redmi with:

- Android SDK
- Android NDK when native components are needed
- Java 21
- Gradle wrapper

## Security

Commands should be signed by the parent device and verified by the child device.
Prefer asymmetric signatures over shared secrets for production.