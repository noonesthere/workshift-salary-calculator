    <script lang="ts">
      let {from, to } = $props();
    </script>

    <div>
      <label for="from" class="font-medium text-gray-700">З </label>
      <input id="from" name="from" type="date" bind:value={from} class="rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500" />

      <label for="to" class="font-medium text-gray-700">По </label>
      <input name="to" type="date" bind:value={to} class="rounded-xl border-gray-300 focus:border-primary-500 focus:ring-primary-500" />
    </div>
