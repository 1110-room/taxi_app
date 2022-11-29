import json
import re
import requests


def get_info_citymobil():
    headers = {
        'authority': 'widget.city-mobil.ru',
        'method': 'POST',
        'path': '/c-api',
        'scheme': 'https',
        'accept': 'application/json, text/plain, */*',
        'accept-encoding': 'gzip, deflate, br',
        'accept-language': 'ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7',
        'content-length': '285',
        'content-type': 'application/json',
        'origin': 'https://city-mobil.ru',
        'referer': 'https://city-mobil.ru/',
        'sec-ch-ua': '"Google Chrome";v="93", " Not;A Brand";v="99", "Chromium";v="93"',
        'sec-ch-ua-mobile': '?0',
        'sec-ch-ua-platform': "Windows",
        'sec-fetch-dest': 'empty',
        'sec-fetch-mode': 'cors',
        'sec-fetch-site': 'same-site',
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36'
    }


    url = 'https://widget.city-mobil.ru/c-api'

    try:
        inp = list(map(float, input().split()))
        route = [[inp[0], inp[1]], [inp[2], inp[3]]]
    except Exception as ex:
        print(ex)
        return False

    data = {
        "method": "getprice",
        "ver": "4.59.0",
        "phone_os": "widget",
        "os_version": "web mobile-web",
        "locale": "ru",
        "latitude": 55.79234,
        "longitude": 49.12205,
        "del_latitude": 55.74411578,
        "del_longitude": 49.17989244,
        "options": [],
        "payment_type": [
            "cash"
        ],
        "tariff_group": [
            2,
            4,
            13,
            7,
            5,
            1
        ],
        "source": "O",
        "hurry": 1
    }
    info = requests.post(url=url, json=data, headers=headers).json()
    try:
        services = info['prices']
    except Exception as ex:
        return False

    taxi_data = {
        'econom': None,
        'comfort': None
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
        for service in services:
            dist = float(re.search(r'(\d+) км', service['label']).group(1))
            tim = int(re.search(r'(\d+) мин', service['label']).group(1))
            for var in varias:
                if service['tariff_info']['name'] == var['name']:
                    taxi_data[var['type']] = service['price']
                    if taxi_data[var['type']] is not None:
                        taxi_data[var['type']] = {'price': int(taxi_data[var['type']])}
                        taxi_data[var['type']]['dist'] = dist
                        taxi_data[var['type']]['time'] = tim
    except:
        return False
    return taxi_data


def get_info_yandex():
    headers = {
        'Accept': '*/*',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36',
        'X-Taxi': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36 turboapp_taxi'
    }

    url = 'https://taxi.yandex.ru/'
    # try:
    #     inp = list(map(float, input().split()))
    #     route = [[inp[0], inp[1]], [inp[2], inp[3]]]
    # except:
    #     return False

    ses = requests.session()

    ses.get(url=url, headers=headers)
    uid = ses.cookies.get_dict()
    res = requests.post(url=url + '3.0/launch/', headers=headers, cookies={'yandexuid': uid['yandexuid']}).json()
    data = {
        "id": res['id'],
        "route": [[49.1786998, 55.7551302], [49.298792, 55.608222]]
        # "skip_estimated_waiting": True,
        # "support_forced_surge": True,
    }
    res = requests.post(url=f'https://ya-authproxy.taxi.yandex.ru/3.0/routestats', json=data)
    info = res.json()
    try:
        services = info['service_levels']
    except:
        return False

    taxi_data = {
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
        tim = int(info['time'].split()[0])
        for service in services:
            for var in varias:
                if service['name'] == var['name']:
                    taxi_data[var['type']] = re.search(r'\d+', service['price']).group(0)
                    if taxi_data[var['type']] is not None:
                        taxi_data[var['type']] = {'price': int(taxi_data[var['type']])}
                        taxi_data[var['type']]['dist'] = dist
                        taxi_data[var['type']]['time'] = tim
    except:
        return False
    return taxi_data


def main():
    print(get_info_yandex())


if __name__ == '__main__':
    main()
