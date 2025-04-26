from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from urllib.parse import urljoin
from datetime import datetime
import pymysql
import time

date_format = "%y.%m.%d"
start_date = datetime(2025, 4, 17)
end_date = datetime(2025, 12, 31)

chrome_options = Options()
chrome_options.add_argument("--headless")
chrome_options.add_argument("--no-sandbox")
chrome_options.add_argument("--disable-dev-shm-usage")
service = Service("/opt/homebrew/bin/chromedriver")

conn = pymysql.connect(
    host="localhost",
    user="root",
    passwd="qwer",
    database="jjimppong",
    charset="utf8mb4"
)
cursor = conn.cursor()
try:
    driver = webdriver.Chrome(service=service, options=chrome_options)
    driver.get("http://www.popply.co.kr/popup")

    WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, "a[href^='/popup/']"))
    )

    time.sleep(2)

    popup_links = driver.find_elements(By.CSS_SELECTOR, "a[href^='/popup/']")
    popup_urls = list(set([
        urljoin("http://www.popply.co.kr", link.get_attribute("href")) for link in popup_links
    ]))

    print(f"총 {len(popup_urls)}개의 팝업 페이지 찾음")

    for i, url in enumerate(popup_urls):
        try:
            driver.get(url)

            try:
                WebDriverWait(driver, 10).until(
                    EC.presence_of_element_located((By.CLASS_NAME, "popup-name"))
                )
                WebDriverWait(driver, 5).until(
                    lambda d: d.find_element(By.CLASS_NAME, 'popup-date').text.strip() != ''
                )
            except:
                print(f"날짜 요소 로딩 실패 또는 내용 없음: {url}")
                continue

            title = driver.find_element(By.CLASS_NAME, 'popup-name').text.strip()
            region = driver.find_element(By.CLASS_NAME, 'popup-location').text.strip()
            date_str = driver.find_element(By.CLASS_NAME, 'popup-date').text.strip()

            try:
                image = driver.find_element(By.CLASS_NAME, "popup-image").find_element(By.TAG_NAME, "img").get_attribute("src")
            except:
                image = "N/A"

            print(f">>> 추출된 날짜 문자열: {date_str}")

            if "-" in date_str and "Invalid" not in date_str:
                start_str, end_str = map(str.strip, date_str.split(" - "))

                try:
                    popup_start_date = datetime.strptime(start_str, date_format)
                    popup_end_date = datetime.strptime(end_str, date_format)

                    if start_date <= popup_start_date <= end_date:
                        print(f"[{i+1}] {title} | {region} | {popup_start_date} - {popup_end_date}")

                        sql = """
                        INSERT INTO popup_store (popup_title, region, popup_image, popup_start_date, popup_end_date)
                        VALUES (%s, %s, %s, %s, %s)
                        """
                        cursor.execute(sql, (
                            title,
                            region,
                            image,
                            popup_start_date.strftime("%Y.%m.%d"),
                            popup_end_date.strftime("%Y.%m.%d") 
                        ))
                        conn.commit()
                    else:
                        print(f"날짜 범위 외: {popup_start_date}")
                except Exception as e:
                    print(f"날짜 파싱 오류 발생 @ {url} -> {e}")
            else:
                print(f"유효하지 않은 날짜 형식: {date_str}")

        except Exception as e:
            print(f"전체 오류 발생 @ {url} -> {e}")

finally:
    driver.quit()
    cursor.close()
    conn.close()

print("모든 팝업 저장 완료!")
