import unittest
from PySide6.QtCore import Qt
from PySide6.QtWidgets import QApplication
from main import create_label

app = QApplication()

class TestMain(unittest.TestCase):

    def test_create_label(self):
        label = create_label()
        self.assertIsNotNone(label)
        self.assertEqual(label.text(), "Hello, PySide6!")
        #self.assertEqual(label.windowFlags(), Qt.WindowFlags())

if __name__ == "__main__":
    unittest.main()
