#!/usr/local/bin/python
from Crypto.PublicKey import RSA
from Crypto.Util import number
import base64 
keydata = None
with open("rsa_public_key.pem", "r") as keyfile:
   keydata = keyfile.read()
pubkey = RSA.importKey(keydata)
 
xml = "<RSAKeyValue>\n"
xml += "<Modulus>\n"
xml += base64.b64encode(number.long_to_bytes(pubkey.n)) + "\n"
xml += "</Modulus>\n"
xml += "<Exponent>\n"
xml += base64.b64encode(number.long_to_bytes(pubkey.e)) + "\n"
xml += "</Exponent>\n"
xml += "</RSAKeyValue>"
 
print xml
