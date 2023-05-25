from bs4 import BeautifulSoup
from sqlalchemy import create_engine, Table, MetaData
from sqlalchemy.orm import sessionmaker
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager

engine = create_engine('h2+h2jdbc://sa:password@localhost:8082/mem:test')  # H2 database URL
Session = sessionmaker(bind=engine)
session = Session()

metadata = MetaData()

book_table = Table('book', metadata, autoload_with=engine)

def insert_into_db(book_info):
    insert_query = book_table.insert().values(
        title=book_info['title'],
        author=book_info['author_and_date'],
        contents=book_info['introduction'],
        rating=float(book_info['rating']) if book_info['rating'] else 0.0,
    )
    session.execute(insert_query)
    session.commit()

def extract_book_info(item):
    try:
        book_title = item.find('span', {'class': 'prod_name'}).text if item.find('span', {'class': 'prod_name'}) else ''
        author_and_date = item.find('span', {'class': 'prod_author'}).text if item.find('span', {'class': 'prod_author'}) else ''
        introduction = item.find('p', {'class': 'prod_introduction'}).text if item.find('p', {'class': 'prod_introduction'}) else ''
        rating = item.find('span', {'class': 'review_klover_text font_size_xxs'}).text if item.find('span', {'class': 'review_klover_text font_size_xxs'}) else ''
    except AttributeError:
        return None
    
    return {
        'title': book_title,
        'author_and_date': author_and_date,
        'introduction': introduction,
        'rating': rating
    }

def scrape_books_from_kyobobook():
    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()))
    driver.get("https://product.kyobobook.co.kr/bestseller/online?period=001")
    wait = WebDriverWait(driver, 10)
    wait.until(EC.presence_of_element_located((By.CLASS_NAME, 'prod_item')))
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    book_items = soup.find_all('li', {'class': 'prod_item'})

    for item in book_items:
        book_info = extract_book_info(item)
        if book_info:
            insert_into_db(book_info)

    driver.quit()

# Start the process
scrape_books_from_kyobobook()
