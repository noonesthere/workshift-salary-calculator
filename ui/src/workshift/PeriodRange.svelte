<script lang="ts">
  let { from = $bindable(), to = $bindable() } = $props<{
    from: string;
    to: string;
  }>();
</script>

<div>
  <label for="from" class="font-medium text-gray-700">З </label>
  <input
    id="from"
    name="from"
    type="date"
    bind:value={from}
    class="rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500"
  />

  <label for="to" class="font-medium text-gray-700">По </label>
  <input
    name="to"
    type="date"
    bind:value={to}
    class="rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500"
  />
</div>

<a
  href={`/export?from=${from}&to=${to}`}
  class="inline-flex items-center gap-2 px-4 py-2
         bg-emerald-600 hover:bg-emerald-700
         text-white font-medium text-sm
         rounded-xl shadow-sm
         transition-colors duration-200
         focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-1"
>
  <i class="fa-solid fa-file-export text-[16px]"></i>
  Експорт в Excel
</a>
