import json
import re
import requests
import sys


def get_info_citymobil(coords):
    headers = {
        'authority': 'widget.city-mobil.ru',
        'method': 'POST',
        'path': '/c-api',
        'scheme': 'https',
        'accept': 'application/json, text/plain, */*',
        'content-type': 'application/json',
        'origin': 'https://city-mobil.ru',
        'sec-ch-ua': '"Google Chrome";v="93", " Not;A Brand";v="99", "Chromium";v="93"',
        'sec-ch-ua-platform': "Windows",
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36'
    }

    url = 'https://widget.city-mobil.ru/c-api'

    data = {
        "method": "getprice",
        "ver": "4.59.0",
        "phone_os": "widget",
        "os_version": "web mobile-web",
        "locale": "ru",
        "latitude": coords[0][0],
        "longitude": coords[0][1],
        "del_latitude": coords[1][0],
        "del_longitude": coords[1][1],
        "options": [],
        "payment_type": ["cash"],
        "tariff_group": [2, 4, 13],
        "source": "O",
        "hurry": 1
    }

    info = requests.post(url=url, json=data, headers=headers).json()

    services = info.get('prices')
    if not services:
        return False

    taxi_data = {
        'econom': None,
        'comfort': None
    }

    varias = [
        {
            'type': 'econom',
            'id': '2'
        },
        {
            'type': 'comfort',
            'id': '4'
        }
    ]

    try:
        for service in services:
            # dist = float(re.search(r'(\d+) км', service['label']).group(1))
            # tim = int(re.search(r'(\d+) мин', service['label']).group(1))
            for var in varias:
                print(service, var['id'])
                if service['id_tariff_group'] == var['id']:
                    price = service['price']
                    if price:
                        taxi_data[var['type']] = {'price': int(price)}
    except:
        return False
    return taxi_data


def get_info_yandex(coords):
    headers = {
        'Accept': '*/*',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36',
        'X-Taxi': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36 turboapp_taxi'
    }

    url = 'https://taxi.yandex.ru/'
    ses = requests.session()
    ses.get(url=url, headers=headers)
    uid = ses.cookies.get_dict()

    res = requests.post(url=url + '3.0/launch/', headers=headers, cookies={'yandexuid': uid['yandexuid']}).json()
    data = {
        "id": res['id'],
        "route": coords
    }
    res = requests.post(url=f'https://ya-authproxy.taxi.yandex.ru/3.0/routestats', json=data)
    info = res.json()
    try:
        services = info['service_levels']
    except:
        return False

    taxi_data = {
        'service': 'yandex',
        'econom': None,
        'comfort': None,
    }

    varias = [
        {
            'type': 'econom',
            'name': 'Эконом'
        },
        {
            'type': 'comfort',
            'name': 'Комфорт'
        }
    ]

    try:
        dist = float(info['distance'].split()[0])
        tim = int(info['time_seconds']) // 60
        for service in services:
            for var in varias:
                if service['name'] == var['name']:
                    taxi_data[var['type']] = re.search(r'\d+', service['price']).group(0)
                    if taxi_data[var['type']] is not None:
                        taxi_data[var['type']] = {'price': int(taxi_data[var['type']])}
                        taxi_data[var['type']]['distance'] = dist
                        taxi_data[var['type']]['time'] = tim
    except:
        return False
    return taxi_data


def main():
    try:
        taxi_type = sys.argv[1]
    except:
        print("Incorrect service")
        return

    try:
        coords = [[float(sys.argv[2]), float(sys.argv[3])], [float(sys.argv[4]), float(sys.argv[5])]]
    except:
        print("Incorrect coordinates")
        return

    match taxi_type:
        case 'yandex':
            res = get_info_yandex(coords)
            if not res:
                print("Incorrect coordinates")
            else:
                print(res)
        case 'citymobil':
            res = get_info_citymobil(coords)
            if not res:
                print("Incorrect coordinates")
            else:
                print(res)
        case _:
            print("Incorrect service")
    return


if __name__ == '__main__':
    main()
