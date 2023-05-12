from Cryptodome.Cipher import AES
import binascii

key = "ABCDEF0123456789"

data = binascii.unhexlify("6d45320e3f16b4eccdfccf8a3f84b3bba08b24bb99900d06662f1cd580")
iv, tag = data[:12], data[-16:]
cipher = AES.new(key.encode('utf-8'), AES.MODE_GCM, iv)
plaintext = cipher.decrypt_and_verify(data[12:-16], tag)
print("{"+ plaintext.decode("utf-8") + "}")