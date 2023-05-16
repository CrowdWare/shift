from Cryptodome.Cipher import AES
import binascii

key = "ABCDEF0123456789"

data = binascii.unhexlify("d502ff41631a0c61f6645c3bb04d194671f2e274e46826924623e42def7205dc262b5376ba29500892148f9cfcc2ba4f")
iv, tag = data[:12], data[-16:]
cipher = AES.new(key.encode('utf-8'), AES.MODE_GCM, iv)
plaintext = cipher.decrypt_and_verify(data[12:-16], tag)
print("{"+ plaintext.decode("utf-8") + "}")