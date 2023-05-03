from PySide6.QtWidgets import QApplication, QLabel

def create_label():
    label = QLabel("Hello, PySide6!")
    label.show()
    return label

if __name__ == "__main__":
    app = QApplication()
    label = create_label()
    app.exec()
