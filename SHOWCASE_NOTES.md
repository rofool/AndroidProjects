# Адаптация практического примера

Источник: https://github.com/maltsevmicyou/VkEducation
Коммит: `b4c673ba2c65fdab3277c144aae300947e43dcb6`.

Корневой пакет преподавателя `io.mmaltsev.vkeducation` заменён на
`com.example.firsttry`. UI карточки сохранён.

| Компонент | Изменение |
| --- | --- |
| AppDetailsContent | Добавлен слот showcase после Developer; скриншоты берутся из модели вместо строк-заглушек. Пустой блок скриншотов скрывается. |
| AppDetailsHeader, AppDescription, Developer, InstallButton, ScreenshotsList, SimilarApps | Перенесена готовая вёрстка. |
| Toolbar | Добавлены описания кнопок для доступности. |
| AppDetailsScreen | Локальные данные и rememberSaveable вместо сетевого ViewModel/Hilt. Используется исходный AppDetailsContent. |
| AppDetailsState, AppDetails, SimilarApp | Перенесены модели и состояние. |
| Category | Убраны аннотации сериализации: сетевого слоя в этой работе нет. |
| presentation/theme | Перенесена тема преподавателя. |
| SimilarAppItem | Выделен в общий AppListItem; адаптивная ширина, квадратная иконка, клик на весь элемент и кнопка «Открыть». |
| strings.xml | Строки преподавателя перенесены в teacher_strings.xml. |

Новые компоненты: ShowcaseApp (граф навигации), AppsListScreen (список),
ShowcaseSection (Pager 3 × 3), AppDetailsData (локальные данные карточки).
Возврат из витрины выполняется через popBackStack("apps", false), без
создания повторного экрана списка в стеке.

В предыдущих ветках этого репозитория hw1 и hw-intents нет экрана списка
приложений: они содержат Greeting и работу с Intent. Поэтому общий элемент
взят из практического репозитория преподавателя и переиспользуется в обеих
частях текущей работы.

Зависимости: Navigation Compose 2.9.5, Foundation, Material Icons Core,
Coil 3.3.0. Тестовые зависимости: AndroidX JUnit 1.3.0, Espresso 3.7.0
(для совместимости с Android 17). Hilt, Room и Retrofit не требуются,
поскольку по заданию данные захардкожены.
