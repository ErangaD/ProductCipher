In this product cipher I have used substitution and transposition.
Substitution: 
	After reading the text file content, content is xored with the key given by the user. (it 
	has set to 'apple' by default). 
Transposition:
	After xoring the content that data stream is divided into 4 bits sets and each 4bit
	is assigned to a different 4bit value.

After the encryption process, the output value is stored in the Output.txt file and the output
vale can also be seen in the textArea on the display.
In the decryption process there must be encrypted data in the Output.txt file. After the 
decryption the decrypted value will be displayed on the display. In order to have the same 
original value it is required to supply the same key that was used to encrypt the data.