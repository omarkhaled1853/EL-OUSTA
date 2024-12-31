// storage_service.dart
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class StorageService {
  final FlutterSecureStorage _storage = FlutterSecureStorage();

  // Read a value from secure storage
  Future<String?> readData(String key) async {
    return await _storage.read(key: key);
  }

  // Write a value to secure storage
  Future<void> writeData(String key, String value) async {
    await _storage.write(key: key, value: value);
  }

  // Delete a value from secure storage
  Future<void> deleteData(String key) async {
    await _storage.delete(key: key);
  }
}
